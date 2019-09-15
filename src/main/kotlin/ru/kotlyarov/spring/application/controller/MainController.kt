package ru.kotlyarov.spring.application.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile
import ru.kotlyarov.spring.application.domain.Message
import ru.kotlyarov.spring.application.domain.User
import ru.kotlyarov.spring.application.repository.MessageRepository
import java.io.File
import java.util.*
import javax.validation.Valid

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
            @Valid message: Message,
            bindingResult: BindingResult,
            map: Model,
            @RequestParam("file") file: MultipartFile): String {

        message.author = user

        if (bindingResult.hasErrors()) {
            val errorMap = ControllerUtils.getErrors(bindingResult)
            map.mergeAttributes(errorMap)
            map.addAttribute("message", message)
        } else {
            saveFile(file, message)
            messageRepository.save(message)
        }

        map.addAttribute("message", null)

        val messages = messageRepository.findAll()
        map.addAttribute("messages", messages)
        return "main"
    }

    private fun saveFile(file: MultipartFile, message: Message) {
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

    @GetMapping("/user-messages/{user}")
    fun userMessages(@AuthenticationPrincipal currentUser: User,
                     @PathVariable user: User,
                     @RequestParam(required = false) message: Message?,
                     model: Model): String {
        val messages = user.getMessages()
        model.addAttribute("userChannel", user)
        model.addAttribute("subscriptionsCount", user.getSubscriptions().size)
        model.addAttribute("subscribersCount", user.getSubscribers().size)
        model.addAttribute("isSubscriber", user.getSubscribers().contains(currentUser))
        model.addAttribute("messages", messages)
        model.addAttribute("message", message)
        model.addAttribute("isCurrentUser", currentUser == user)
        return "userMessages"
    }

    @PostMapping("/user-messages/{user}")
    fun updateMessage(@AuthenticationPrincipal currentUser: User,
                      @PathVariable user: User,
                      @RequestParam("id") message: Message,
                      @RequestParam("text") text: String,
                      @RequestParam("tag") tag: String,
                      @RequestParam("file") file: MultipartFile): String {

        if (message.author == currentUser) {
            if (text.isNotEmpty()) {
                message.text = text
            }
            if (tag.isNotEmpty()) {
                message.tag = tag
            }
        }
        saveFile(file, message)
        messageRepository.save(message)

        return "redirect:/user-messages/${user.id}"
    }
}