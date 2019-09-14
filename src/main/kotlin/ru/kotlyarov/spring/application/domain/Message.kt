package ru.kotlyarov.spring.application.domain

import org.hibernate.validator.constraints.Length
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
class Message(
        @field:NotBlank(message = "Please fill the message")
        @Length(max = 2048, message = "The message to long")
        var text: String,
        @field:NotBlank(message = "Please fill the tag")
        @Length(max = 255, message = "The tag to long")
        var tag: String,
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "user_id")
        var author: User?) {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private var id: Long = 0
    private var filename: String? = null

    fun setId(id: Long) {
        this.id = id
    }

    fun getId(): Long {
        return id
    }

    fun setFilename(filename: String) {
        this.filename = filename
    }

    fun getFilename(): String? {
        return filename
    }

    fun getAuthorName(): String {
        return if (author == null) {
            "<none>"
        } else {
            author!!.username
        }
    }
}