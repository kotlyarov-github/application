package ru.kotlyarov.spring.application.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile
import ru.kotlyarov.spring.application.domain.Message
import ru.kotlyarov.spring.application.domain.User
import ru.kotlyarov.spring.application.repository.MessageRepository
import java.io.File
import java.util.*

@Controller
class MainController() {

    @Autowired
    lateinit var messageRepository: MessageRepository

    @Value("\${upload.path}")
    lateinit var uploadPath: String


    @GetMapping("/")
    fun login(model: Model): String {
        return "/greeting"
    }

    @GetMapping("/main")
    fun main(@RequestParam(required = false, defaultValue = "") filter: String?, map: Model): String {
        val messages: Iterable<Message>
        if (filter.isNullOrEmpty()) {
            messages = messageRepository.findAll()
        } else {
            messages = messageRepository.findByTag(tag = filter!!)
        }
        map.addAttribute("messages", messages)
        map.addAttribute("filter", filter)
        return "main"
    }

    @PostMapping("/main")
    fun add(@AuthenticationPrincipal user: User,
            @RequestParam text: String,
            @RequestParam tag: String, map: Model,
            @RequestParam("file") file: MultipartFile): String {
        val message = Message(text, tag, user)

        if (file.originalFilename!!.isNotEmpty()) {
            val uploadDir = File(uploadPath)
            if (!uploadDir.exists()) {
                uploadDir.mkdir()
            }
            val uuidName = UUID.randomUUID().toString()
            val filename = "${uuidName}_${file.originalFilename}"
            file.transferTo(File("/$uploadDir/$filename"))
            message.setFilename(filename)
        }
        messageRepository.save(message)
        val messages = messageRepository.findAll()

        map.addAttribute("messages", messages)
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