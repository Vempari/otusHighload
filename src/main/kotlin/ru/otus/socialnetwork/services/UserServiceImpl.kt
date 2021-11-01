package ru.otus.socialnetwork.services

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import ru.otus.socialnetwork.dto.RegisterRequestDto
import ru.otus.socialnetwork.entity.Client
import ru.otus.socialnetwork.entity.Hobbies
import ru.otus.socialnetwork.entity.User
import ru.otus.socialnetwork.enums.RolesEnum
import ru.otus.socialnetwork.enums.Status
import ru.otus.socialnetwork.repositories.EntityRepository
import ru.otus.socialnetwork.repositories.RoleRepository
import ru.otus.socialnetwork.repositories.UserRepository
import java.lang.IllegalStateException
import java.util.stream.Collectors
import javax.transaction.Transactional

@Service
@Transactional
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
    private val entityRepository: EntityRepository
) : UserService {

    override fun register(registerForm: RegisterRequestDto): User? {
        val username = userRepository.findUserByUsername(registerForm.username)

        if (username != null) {
            throw IllegalStateException("$username is registered already")
        }

        val hobbies = registerForm.hobbies?.split(",")
            ?.stream()?.peek { it.replace("(?m)^[ \t]*\r?\n", "") }
            ?.map { Hobbies(name = it) }
            ?.collect(Collectors.toList())

        val client = Client(
            name = registerForm.name ?: throw IllegalStateException("name is required"),
            surname = registerForm.surname ?: throw IllegalStateException("surname is required"),
            gender = registerForm.gender ?: throw IllegalStateException("gender is required"),
            city = registerForm.city ?: throw IllegalStateException("city is required"),
            hobbies = hobbies
        )
        val user = User(
            username = registerForm.username ?: throw IllegalStateException("username is required"),
            password = bCryptPasswordEncoder.encode(registerForm.password),
            client = client,
            status = Status.ACTIVE,
            roles = listOf(roleRepository.findByName(RolesEnum.ROLE_USER))
        )

        entityRepository.persist(client)
        entityRepository.persist(user)
        hobbies?.forEach { entityRepository.persist(it) }
        return user
    }

    override fun getAll(): List<User>? {
         return userRepository.getAllUsers()
    }

    override fun findByUsername(username: String?): User? {
        return userRepository.findUserByUsername(username ?: throw IllegalStateException("Can`t find $username"))
    }

    override fun findById(id: Long): User {
        return entityRepository.getRequired(User::class.java, id) ?: throw IllegalStateException("Can`t find client with id: $id")
    }

    override fun delete(id: Long): Boolean {
        entityRepository.delete(entityRepository.getRequired(User::class.java, id) ?: return false)
        return true
    }
}