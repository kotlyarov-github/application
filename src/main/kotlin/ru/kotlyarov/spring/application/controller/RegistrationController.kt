package ru.kotlyarov.spring.application.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import ru.kotlyarov.spring.application.domain.User
import ru.kotlyarov.spring.application.service.UserService
import javax.validation.Valid

@Controller
class RegistrationController() {

    @Autowired
    lateinit var userService: UserService

    @GetMapping("/registration")
    fun registration(model: Model): String {
        return "registration"
    }

    @PostMapping("/registration")
    fun addUser(@Valid user: User, bindingResult: BindingResult, map: Model): String {
        if (user.password != null && user.password != user.getPassword2()) {
            map.addAttribute("passwordError", "Passwords are different")
        }

        if (bindingResult.hasErrors()) {
            val errors = ControllerUtils.getErrors(bindingResult)
            map.mergeAttributes(errors)
            return "registration"
        }

        if (!userService.addUser(user)) {
            map.addAttribute("usernameError", "User exist or email taken")
            return "registration"
        }
        return "redirect:/login"
    }

    @GetMapping("/activate/{code}")
    fun activate(map: Model, @PathVariable code: String): String {
        val isActivated = userService.activateUser(code)

        if (isActivated) {
            map.addAttribute("message", "User successfully activated")
        } else {
            map.addAttribute("message", "Activation code isn't found")
        }
        return "login"
    }
}