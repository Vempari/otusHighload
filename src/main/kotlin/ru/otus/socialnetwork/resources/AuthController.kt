package ru.otus.socialnetwork.resources

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import ru.otus.socialnetwork.configurations.security.JwtTokenProvider
import ru.otus.socialnetwork.dto.AuthenticationRequestDto
import ru.otus.socialnetwork.dto.RegisterRequestDto
import ru.otus.socialnetwork.services.UserService
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

@Controller
class AuthController(
    private val authenticationManager: AuthenticationManager,
    private val jwtTokenProvider: JwtTokenProvider,
    private val userService: UserService
) {

    @RequestMapping(value = ["/ws/auth/login"], method = [RequestMethod.GET])
    fun login(model: Model) : String {
        model.addAttribute("requestDto", AuthenticationRequestDto())
        return "login"
    }

    @RequestMapping(value = ["/ws/auth/loggin"], method = [RequestMethod.POST])
    fun loggin(@ModelAttribute("requestDto") requestDto: AuthenticationRequestDto, model: Model,
               servletResponse: HttpServletResponse) : String {
        try {
            val username = requestDto.username
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(username, requestDto.password))

            val user = userService.findByUsername(username)

            val token = jwtTokenProvider.createToken(
                username,
                user?.roles ?: throw UsernameNotFoundException("User with username: $username not found")
            )

            val cookie = Cookie("token", URLEncoder.encode(token, StandardCharsets.UTF_8.name()))
            cookie.path = "/"

            servletResponse.addCookie(cookie)

            return "redirect:/ws/public/clients"
        } catch (e: Exception) {
            return e.message ?: ""
        }
    }

    @RequestMapping(value = ["/ws/auth/register"], method = [RequestMethod.GET])
    fun register(model: Model) : String {
        model.addAttribute("requestDto", RegisterRequestDto())
        return "register"
    }

    @RequestMapping(value = ["/ws/auth/logout"], method = [RequestMethod.GET])
    fun logout(model: Model, servletResponse: HttpServletResponse) : String {
        val cookie = Cookie("token", "")
        cookie.maxAge = 0
        servletResponse.addCookie(cookie)
        return "index"
    }

    @RequestMapping(value = ["/ws/auth/save_client"], method = [RequestMethod.POST])
    fun saveClient(@ModelAttribute requestDto: RegisterRequestDto, model: Model) : String {
        try {
            userService.register(requestDto) ?: throw IllegalStateException("Can`t register client")
            return "redirect:/ws/auth/login"
        } catch (e: Exception) {
            return e.message ?: ""
        }
    }
}