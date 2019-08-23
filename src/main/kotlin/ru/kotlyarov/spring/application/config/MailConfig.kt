package ru.kotlyarov.spring.application.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl

@Configuration
class MailConfig {
    @Value("\${spring.mail.properties.mail.smtp.auth}")
    private val auth: String? = null
    @Value("\${spring.mail.properties.mail.smtp.starttls.enable}")
    private val enable: String? = null
    @Value("\${spring.mail.host}")
    private lateinit var host: String
    @Value("\${spring.mail.username}")
    private lateinit var username: String
    @Value("\${spring.mail.password}")
    private lateinit var password: String
    @Value("\${spring.mail.port}")
    private var port: Int = 0
    @Value("\${spring.mail.protocol}")
    private lateinit var protocol: String
    @Value("\${mail.debug}")
    private lateinit var debug: String

    @Bean
    fun getMailSender(): JavaMailSender? {

        val jms = JavaMailSenderImpl()

        jms.host = this@MailConfig.host
        jms.username = this@MailConfig.username
        jms.password = this@MailConfig.password
        jms.port = this@MailConfig.port
        val mailRepository = jms.javaMailProperties
        mailRepository.setProperty("mail.transport.protocol", protocol)
        mailRepository.setProperty("mail.debug", debug)
        mailRepository.setProperty("mail.smtp.auth", auth);
        mailRepository.setProperty("mail.smtp.starttls.enable", enable);

        return jms
    }
}