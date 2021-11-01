package ru.otus.socialnetwork.repositories

import ru.otus.socialnetwork.entity.User

interface UserRepository {

    fun findUserByUsername(username: String?) : User?
    fun getAllUsers() : List<User>?
}