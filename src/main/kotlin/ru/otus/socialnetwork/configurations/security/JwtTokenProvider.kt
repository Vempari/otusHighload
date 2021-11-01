package ru.otus.socialnetwork.configurations.security

import io.jsonwebtoken.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import ru.otus.socialnetwork.entity.Role
import ru.otus.socialnetwork.enums.RolesEnum
import java.lang.IllegalStateException
import java.util.*
import java.util.stream.Collectors
import javax.annotation.PostConstruct
import javax.servlet.http.HttpServletRequest


@Component
class JwtTokenProvider {

    @Value("\${jwt.token.secret}")
    private lateinit var secret: String

    @Value("\${jwt.token.expired}")
    private lateinit var validityInMilliseconds: String

    @Autowired
    lateinit var userDetailService: UserDetailsService

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder? {
        return BCryptPasswordEncoder()
    }

    @PostConstruct
    protected fun init() {
        secret = Base64.getEncoder().encodeToString(secret.toByteArray())
    }

    fun createToken(username: String?, roles: List<Role>): String? {
        val claims: Claims = Jwts.claims().setSubject(username)
        claims.put("roles", getRoleNames(roles))
        val now = Date()
        val validity = Date(now.time + validityInMilliseconds.toLong())
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(SignatureAlgorithm.HS256, secret)
            .compact()
    }

    fun getAuthentication(token: String?): Authentication? {
        val userDetails: UserDetails = userDetailService.loadUserByUsername(getUsername(token))
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    fun getUsername(token: String?): String? {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).body.subject
    }

    fun resolveToken(req: HttpServletRequest): String? {
        val cookies = req.cookies
        return cookies.filter { it.name == "token" }.map { it.value }.lastOrNull()
    }

    fun validateToken(token: String?): Boolean {
        try {
            val claims: Jws<Claims> = Jwts.parser().setSigningKey(secret).parseClaimsJws(token)
            return !claims.body.expiration.before(Date())
        } catch (e: JwtException) {
            throw IllegalAccessException("JWT token is expired or invalid")
        } catch (e: IllegalArgumentException) {
            throw IllegalAccessException("JWT token is expired or invalid")
        }
    }

    private fun getRoleNames(userRoles: List<Role>): MutableList<RolesEnum>? {
        return userRoles.stream()
            .map { it.name }
            .collect(Collectors.toList())
    }
}