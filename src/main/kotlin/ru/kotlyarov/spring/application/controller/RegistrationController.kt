package ru.kotlyarov.spring.application.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import ru.kotlyarov.spring.application.domain.Role
import ru.kotlyarov.spring.application.domain.User
import ru.kotlyarov.spring.application.repository.UserRepository
import java.util.*

@Controller
class RegistrationController() {

    @Autowired
    lateinit var userRepository: UserRepository

    @GetMapping("/registration")
    fun registration(model: Model): String {
        return "registration"
    }

    @PostMapping("/registration")
    fun addUser(user: User, map: Model): String {
        val user1 = userRepository.findByUsername(username = user.username!!)
        if (user1 != null) {
            map.addAttribute("message", "User exist")
            return "registration"
        }

        user.roles = Collections.singleton(Role.USER)
        userRepository.save(user)
        return "redirect:/login"
    }
}