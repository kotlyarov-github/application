package ru.kotlyarov.spring.application.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import ru.kotlyarov.spring.application.domain.Role
import ru.kotlyarov.spring.application.domain.User
import ru.kotlyarov.spring.application.repository.UserRepository

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('ADMIN')")
class UserController {
    @Autowired
    lateinit var userRepository: UserRepository

    @GetMapping
    fun userList(model: Model): String {
        model.addAttribute("users", userRepository.findAll())
        return "userList"
    }

    @GetMapping("{user}")
    fun userEditForm(@PathVariable user: User, map: Model): String {
        map.addAttribute("user", user)
        map.addAttribute("roles", Role.values())
        return "userEdit"
    }

    @GetMapping("/delete/{user}")
    fun userDeleteForm(@PathVariable user: User, map: Model): String {
        map.addAttribute("user", user)
        map.addAttribute("roles", Role.values())
        //    map.addAttribute("users", userRepository.findAll())
        return "userDelete"
    }

    @PostMapping("/delete/delete")
    fun userDelete(
            @RequestParam form: Map<String, String>,
            @RequestParam("userId") user: User): String {
        userRepository.delete(user)
        return "redirect:/user"
    }

    @PostMapping("/edit")
    fun userSave(
            @RequestParam username: String,
            @RequestParam form: Map<String, String>,
            @RequestParam("userId") user: User): String {
        user.username = username
        val roles = Role.values().map { it.name }.toSet()

        user.roles!!.clear()

        form.forEach { (k, _) ->
            if (roles.contains(k)) {
                user.roles!!.add(Role.valueOf(k))
            }
        }
        userRepository.save(user)
        return "redirect:/user"
    }
}