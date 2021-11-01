package ru.otus.socialnetwork.repositories

import ru.otus.socialnetwork.entity.Role
import ru.otus.socialnetwork.enums.RolesEnum

interface RoleRepository {
    fun findByName(role: RolesEnum) : Role
}