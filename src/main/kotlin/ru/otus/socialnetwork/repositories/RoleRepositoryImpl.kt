package ru.otus.socialnetwork.repositories

import org.springframework.stereotype.Repository
import ru.otus.socialnetwork.entity.Role
import ru.otus.socialnetwork.enums.RolesEnum

@Repository
class RoleRepositoryImpl : RoleRepository, AbstractRepository<Role, Long>(Role::class.java) {
    override fun findByName(role: RolesEnum): Role {
        val hql = "FROM Role as r WHERE r.name = '$role'"
        return session().createQuery(hql, Role::class.java).uniqueResult()
    }
}