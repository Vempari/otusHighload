package ru.otus.socialnetwork.resources

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import ru.otus.socialnetwork.configurations.security.JwtTokenProvider
import ru.otus.socialnetwork.entity.Client
import ru.otus.socialnetwork.repositories.UserRepository
import ru.otus.socialnetwork.services.ClientService
import ru.otus.socialnetwork.services.UserService
import java.lang.IllegalStateException
import javax.servlet.http.HttpServletRequest
import javax.websocket.server.PathParam

@Controller
class ClientsController(
    private val clientService: ClientService,
    private val userService: UserService,
    private val jwtTokenProvider: JwtTokenProvider
    ) {

    @RequestMapping(value = ["/ws/public/clients"], method = [RequestMethod.GET])
    fun listClients(model: Model, servletRequest: HttpServletRequest): String {
        model.addAllAttributes(mapOf("clientList" to clientService.getList(), "currentClient" to getCurrentClient(servletRequest)))
        return "clients"
    }

    @RequestMapping(value = ["/ws/public/add_friend/{id}"], method = [RequestMethod.GET])
    fun addFriend(@PathVariable id: Long?, model: Model, servletRequest: HttpServletRequest): String {
        clientService.addFriendToClient(getCurrentClient(servletRequest), id ?: throw IllegalStateException("client doesn`t have id "))
        return "redirect:/ws/public/clients"
    }

    @RequestMapping(value = ["/ws/public/friends_list"], method = [RequestMethod.GET])
    fun friendsList(model: Model, servletRequest: HttpServletRequest) : String {
        val currentClient = getCurrentClient(servletRequest)

        model.addAttribute("friends", currentClient.friends)
        return "friends"
    }

    private fun getCurrentClient(servletRequest: HttpServletRequest) : Client {
        val token = servletRequest.cookies.lastOrNull { it.name == "token" }?.value ?: throw IllegalStateException("Can`t find token for client")
        val username = jwtTokenProvider.getUsername(token)
        return userService.findByUsername(username)?.client ?: throw IllegalStateException("Cant find client with username $username")
    }

}