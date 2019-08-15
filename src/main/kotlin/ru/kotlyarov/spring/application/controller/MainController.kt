package ru.kotlyarov.spring.application.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import ru.kotlyarov.spring.application.domain.Message
import ru.kotlyarov.spring.application.domain.User
import ru.kotlyarov.spring.application.repository.MessageRepository

@Controller
class MainController() {

    @Autowired
    lateinit var messageRepository: MessageRepository

    @GetMapping("/")
    fun login(model: Model): String {
        return "/greeting"
    }

    @GetMapping("/main")
    fun main(@RequestParam(required = false, defaultValue = "") filter: String?, map: Model): String {
        var message = messageRepository.findAll()

        if (filter.isNullOrEmpty()) {
            message = messageRepository.findAll()
        } else {
            message = messageRepository.findByTag(tag = filter!!)
        }
        map.addAttribute("messages", message)
        map.addAttribute("filter", filter)
        return "main"
    }

    @PostMapping("/main")
    fun add(@AuthenticationPrincipal user: User,
            @RequestParam(required = false, defaultValue = "") filter: String?,
            @RequestParam text: String,
            @RequestParam tag: String, map: Model): String {
        val message = Message(text, tag, user)
        messageRepository.save(message)
        val messages = messageRepository.findAll()
        map.addAttribute("messages", messages)
        map.addAttribute("filter", filter)
        return "main"
    }

    @PostMapping("/filter")
    fun find(@RequestParam filter: String, map: Model): String {
        lateinit var listMessages: List<Message>
        if (filter.isEmpty()) {
            listMessages = messageRepository.findAll()
        } else {
            listMessages = messageRepository.findByTag(tag = filter)
        }
        map.addAttribute("messages", listMessages)
        return "main"
    }
}