package ru.kotlyarov.spring.application.domain.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class CaptchaResponseDto(
        @JsonProperty("success")
        val success: Boolean,       //true|false
        @JsonProperty("challenge_ts")
        val challenge_ts: String?,  // timestamp of the challenge load (ISO format yyyy-MM-dd'T'HH:mm:ssZZ)
        @JsonProperty("hostname")
        val hostname: String?,      // the hostname of the site where the reCAPTCHA was solved
        @JsonProperty("error-codes")
        val errorCodes: List<Any>?        // optional
)