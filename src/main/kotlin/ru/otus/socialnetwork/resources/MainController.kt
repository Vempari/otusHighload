package ru.otus.socialnetwork.resources

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@Controller
class MainController {
    @RequestMapping(value = ["/", "/index"], method = [RequestMethod.GET])
    fun index(model: Model) : String {
        return "index"
    }
}