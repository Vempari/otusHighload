package ru.otus.socialnetwork.dto

import ru.otus.socialnetwork.enums.Gender


data class RegisterRequestDto(
    var username: String? = null,
    var password: String? = null,
    var gender: Gender? = null,
    var name: String? = null,
    var surname: String? = null,
    var city: String? = null,
    var hobbies: String? = null
)
