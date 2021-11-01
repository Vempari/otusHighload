package ru.otus.socialnetwork.configurations.security

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDateTime

class JwtUser(
    private val id: Long,
    private val username: String,
    private val firstName: String,
    private val lastName: String,
    private val password: String,
    private val enabled: Boolean,
    private val lastPasswordResetDate: LocalDateTime,
    private val authorities: MutableCollection<GrantedAuthority>
) : UserDetails {

    override fun getAuthorities(): MutableCollection<GrantedAuthority> {
        return authorities
    }

    @JsonIgnore
    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return username
    }

    @JsonIgnore
    override fun isAccountNonExpired(): Boolean {
        return true
    }

    @JsonIgnore
    override fun isAccountNonLocked(): Boolean {
        return true
    }

    @JsonIgnore
    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return enabled
    }

}