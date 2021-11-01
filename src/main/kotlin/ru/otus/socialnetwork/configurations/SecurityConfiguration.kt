package ru.otus.socialnetwork.configurations

import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint
import ru.otus.socialnetwork.configurations.security.JwtConfigurer
import ru.otus.socialnetwork.configurations.security.JwtTokenProvider

@EnableWebSecurity
class SecurityConfiguration(val jwtTokenProvider: JwtTokenProvider) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity?) {
        http
            ?.csrf()?.disable()
            ?.sessionManagement()?.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            ?.and()?.exceptionHandling()?.authenticationEntryPoint(http403ForbiddenEntryPoint())
            ?.and()
            ?.authorizeRequests()
            ?.antMatchers("/", "/ws/auth/**")?.permitAll()
            ?.antMatchers("/ws/public/**")?.hasRole("USER")
            ?.antMatchers("/ws/admin/**")?.hasRole("ADMIN")?.anyRequest()?.authenticated()
            ?.and()
            ?.apply(JwtConfigurer(jwtTokenProvider))
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    @Bean
    fun http403ForbiddenEntryPoint(): AuthenticationEntryPoint? {
        return Http403ForbiddenEntryPoint()
    }

}