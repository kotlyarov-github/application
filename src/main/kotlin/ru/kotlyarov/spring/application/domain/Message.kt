package ru.kotlyarov.spring.application.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Message(
        var text: String?,
        var tag: String?,
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private var id: Int = 0
)