package ru.kotlyarov.spring.application.domain

import javax.persistence.*


@Entity
@Table(name = "usr")
data class User(var username: String?,
                var password: String?,
                var active: Boolean = true,
                @Id
                @GeneratedValue(strategy = GenerationType.AUTO)
                var id: Long = 0) {

    @ElementCollection(targetClass = Role::class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = [JoinColumn(name = "user_id")])
    @Enumerated(EnumType.STRING)
    var roles: MutableSet<Role?>? = null
}
