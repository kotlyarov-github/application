package ru.kotlyarov.spring.application.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import ru.kotlyarov.spring.application.domain.User
import ru.kotlyarov.spring.application.repository.UserRepository

@Service
class UserService : UserDetailsService {
    @Autowired
    private lateinit var userRepository: UserRepository

    override fun loadUserByUsername(name: String): User? {
        return userRepository.findByUsername(name)
    }

}