package ru.otus.socialnetwork.configurations.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import ru.otus.socialnetwork.entity.Role
import ru.otus.socialnetwork.entity.User
import ru.otus.socialnetwork.enums.Status
import java.lang.IllegalStateException
import java.util.stream.Collectors
import javax.transaction.Transactional

fun create(user : User) : JwtUser {
    return JwtUser(
        user.id ?: throw IllegalStateException("Can`t create JwtUser with null id"),
        user.username,
        user.client.name,
        user.client.surname,
        user.password,
        Status.ACTIVE === user.status,
        user.entityUpdated,
        mapToGrantedAuthorities(user.roles)
    )
}

private fun mapToGrantedAuthorities(userRoles: List<Role>): MutableList<GrantedAuthority> {
    return userRoles.stream()
        .map {SimpleGrantedAuthority(it.name.toString())}
        .collect(Collectors.toList())
}
