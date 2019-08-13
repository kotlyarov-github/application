package ru.kotlyarov.spring.application.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.kotlyarov.spring.application.domain.Message

interface MessageRepository : JpaRepository<Message, Long> {
    fun findByTag(tag: String): List<Message>
}