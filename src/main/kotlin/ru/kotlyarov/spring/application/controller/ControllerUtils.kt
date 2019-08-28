package ru.kotlyarov.spring.application.controller

import org.springframework.validation.BindingResult

class ControllerUtils {
    companion object {
        fun getErrors(bindingResult: BindingResult): Map<String, String?> {
            return bindingResult.fieldErrors.map { t -> t.field + "Error" to t.defaultMessage }.toMap()
        }
    }
}