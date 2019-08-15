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

    fun getAuthorName(): String {
        return if (user == null) {
            "<none>"
        } else {
            user!!.username
        }
    }
}