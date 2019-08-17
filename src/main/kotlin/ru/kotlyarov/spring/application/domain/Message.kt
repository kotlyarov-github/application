package ru.kotlyarov.spring.application.domain

import javax.persistence.*

@Entity
data class Message(
        var text: String?,
        var tag: String?,
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "user_id")
        var user: User?
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private var id: Int = 0
    private var filename: String? = null

    fun setId(id: Int) {
        this.id = id
    }

    fun getId(): Int {
        return id
    }

    fun setFilename(filename: String) {
        this.filename = filename
    }

    fun getFilename(): String? {
        return filename
    }

    fun getAuthorName(): String {
        return if (user == null) {
            "<none>"
        } else {
            user!!.username
        }
    }
}