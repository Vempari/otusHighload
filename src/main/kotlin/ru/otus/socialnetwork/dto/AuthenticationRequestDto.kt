package ru.otus.socialnetwork.dto

data class AuthenticationRequestDto(
    var username: String? = null,
    var password: String? = null
)
