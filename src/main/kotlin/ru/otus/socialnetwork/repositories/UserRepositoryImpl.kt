package ru.otus.socialnetwork.repositories

import org.springframework.stereotype.Repository
import ru.otus.socialnetwork.entity.User

@Repository
class UserRepositoryImpl : UserRepository, AbstractRepository<User, Long>(User::class.java) {
    override fun findUserByUsername(username: String?): User? {
        val hql = "FROM User as r WHERE r.username = '$username'"
        return session().createQuery(hql, User::class.java).uniqueResult()
    }

    override fun getAllUsers(): List<User>? {
        val hql = "FROM User"
        return session().createQuery(hql, User::class.java).list()
    }
}