package ru.kotlyarov.spring.application.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.kotlyarov.spring.application.domain.User

interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): User?
    fun findByActivationCode(code: String): User?
    fun findByEmail(email: String): User?
}

