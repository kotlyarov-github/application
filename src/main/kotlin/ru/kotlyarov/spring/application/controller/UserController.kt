package ru.kotlyarov.spring.application.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import ru.kotlyarov.spring.application.domain.Role
import ru.kotlyarov.spring.application.domain.User
import ru.kotlyarov.spring.application.service.UserService


@Controller
@RequestMapping("/user")
class UserController {
    @Autowired
    lateinit var userService: UserService

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    fun userList(model: Model): String {
        model.addAttribute("users", userService.findAll())
        return "userList"
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{user}")
    fun userEditForm(@PathVariable user: User, map: Model): String {
        map.addAttribute("user", user)
        map.addAttribute("roles", Role.values())
        return "userEdit"
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/delete/{user}")
    fun userDeleteForm(@PathVariable user: User, map: Model): String {
        map.addAttribute("user", user)
        map.addAttribute("roles", Role.values())
        //    map.addAttribute("users", userRepository.findAll())
        return "userDelete"
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/delete/delete")
    fun userDelete(
            @RequestParam form: Map<String, String>,
            @RequestParam("userId") user: User): String {
        userService.delete(user)
        return "redirect:/user"
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/edit")
    fun userSave(
            @RequestParam username: String,
            @RequestParam form: Map<String, String>,
            @RequestParam("userId") user: User): String {
        userService.saveUser(user, username, form)
        return "redirect:/user"
    }

    @GetMapping("profile")
    fun getProfile(model: Model, @AuthenticationPrincipal user: User): String {
        model.addAttribute("username", user.username)
        model.addAttribute("email", user.getEmail())
        return "profile"
    }

    @PostMapping("profile")
    fun updateProfile(
            @AuthenticationPrincipal user: User,
            @RequestParam password: String,
            @RequestParam email: String?
    ): String {
        userService.updateProfile(user, password, email)
        return "redirect:/user/profile"
    }

    @GetMapping("subscribe/{user}")
    fun subscribe(@AuthenticationPrincipal currentUser: User,
                  @PathVariable user: User): String {
        userService.subscribe(currentUser, user)
        return "redirect:/user-messages/${user.id}"
    }

    @GetMapping("unsubscribe/{user}")
    fun unsubscribe(@AuthenticationPrincipal currentUser: User,
                    @PathVariable user: User): String {
        userService.unsubscribe(currentUser, user)
        return "redirect:/user-messages/${user.id}"
    }


    @GetMapping("{type}/{user}/list")
    fun userList(
            model: Model,
            @PathVariable user: User,
            @PathVariable type: String
    ): String {
        model.addAttribute("userChannel", user)
        model.addAttribute("type", type)

        if ("subscriptions" == type) {
            model.addAttribute("users", user.getSubscriptions())
        } else {
            model.addAttribute("users", user.getSubscribers())
        }

        return "subscriptions"
    }
}