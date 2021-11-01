package ru.otus.socialnetwork.configurations.security

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import ru.otus.socialnetwork.services.UserService
import javax.transaction.Transactional

@Service
class JwtUserDetailService (private val userService: UserService) : UserDetailsService {

    @Transactional
    override fun loadUserByUsername(username: String?): UserDetails {
        val user = userService.findByUsername(username)

        return create(user ?: throw UsernameNotFoundException("User with username: $username not found"))
    }

}