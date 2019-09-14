package ru.kotlyarov.spring.application.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class MvcConfig : WebMvcConfigurer {
    @Value("\${upload.path}")
    lateinit var uploadPath: String

    @Bean
    fun getRestTemplate(): RestTemplate {
        return RestTemplate()
    }

    override fun addViewControllers(registry: ViewControllerRegistry) {
        registry.addViewController("/login").setViewName("login")
    }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/img/**")
                .addResourceLocations("file://$uploadPath/")
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/")
    }
}