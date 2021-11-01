package ru.otus.socialnetwork.services

import ru.otus.socialnetwork.dto.RegisterRequestDto
import ru.otus.socialnetwork.entity.User

interface UserService {
    fun register(registerForm: RegisterRequestDto) : User?
    fun getAll() : List<User>?
    fun findByUsername(username: String?) : User?
    fun findById(id: Long) : User?
    fun delete(id: Long) : Boolean
}