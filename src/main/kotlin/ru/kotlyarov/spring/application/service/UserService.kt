package ru.kotlyarov.spring.application.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import ru.kotlyarov.spring.application.domain.Role
import ru.kotlyarov.spring.application.domain.User
import ru.kotlyarov.spring.application.repository.UserRepository
import java.util.*

@Service
class UserService : UserDetailsService {
    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var mailSender: MailSender

    override fun loadUserByUsername(name: String): User? {
        return userRepository.findByUsername(name)
    }

    fun addUser(user: User): Boolean {
        val userFromDb = userRepository.findByUsername(username = user.username)

        if (userFromDb != null) {
            return false
        }
        //Check email is null or taken other user return false
        val email = user.getEmail()
        if (email.isNullOrEmpty()) {
            return false
        } else {
            val userEmailFromDb = userRepository.findByEmail(user.getEmail()!!)
            if (userEmailFromDb != null) {
                return false
            }
        }

        user.roles = Collections.singleton(Role.USER)
        user.setActivationCod(UUID.randomUUID().toString())
        userRepository.save(user)

        sendEmail(user)

        return true
    }

    private fun sendEmail(user: User) {
        if (!user.getEmail().isNullOrEmpty()) {

            val message = "Hello ${user.username} This is activation cod for application link: " +
                    "http://localhost:8080/activate/${user.getActivationCod()}"
            mailSender.send(user.getEmail()!!, "Activation cod", message)
        }
    }

    fun activateUser(code: String): Boolean {
        val user = userRepository.findByActivationCode(code) ?: return false

        user.setActivationCod(null)
        userRepository.save(user)

        return true
    }

    fun findAll(): MutableList<User> {
        return userRepository.findAll()
    }

    fun saveUser(user: User, username: String, form: Map<String, String>) {
        user.username = username
        val roles = Role.values().map { it.name }.toSet()

        user.roles!!.clear()

        form.forEach { (k, _) ->
            if (roles.contains(k)) {
                user.roles!!.add(Role.valueOf(k))
            }
        }
        userRepository.save(user)
        sendEmail(user)
    }

    fun delete(user: User) {
        userRepository.delete(user)
    }

    fun updateProfile(user: User, password: String, email: String?) {
        val userEmail = user.getEmail()
        val isEmailChanged = (email != null && userEmail != email) ||
                (userEmail != null && userEmail != email)

        if (isEmailChanged) {
            user.setEmail(email!!)
            user.setActivationCod(UUID.randomUUID().toString())
            sendEmail(user)
        }

        if (password.isNotEmpty()) {
            user.password = password
        }
        userRepository.save(user)
    }

}