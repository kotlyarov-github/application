package ru.kotlyarov.spring.application.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class MailSender {
    @Value("\${spring.mail.username}")
    private lateinit var username: String
    @Autowired
    private lateinit var mailSender: JavaMailSender

    fun send(emailTo: String, subject: String, message: String) {
        val mailMailMessage = SimpleMailMessage()

        mailMailMessage.setFrom(username)
        mailMailMessage.setTo(emailTo)
        mailMailMessage.setSubject(subject)
        mailMailMessage.setText(message)

        mailSender.send(mailMailMessage)
    }
}