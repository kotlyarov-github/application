package ru.kotlyarov.spring.application.domain

import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*


@Entity
@Table(name = "usr")
class User(private var username: String?,
           private var password: String?,
           private var active: Boolean = true,
           @Id
                @GeneratedValue(strategy = GenerationType.AUTO)
           var id: Long = 0) : UserDetails {

    fun isAdmin(): Boolean {
        return roles!!.contains(Role.ADMIN)
    }

    fun setUsername(username: String) {
        this.username = username
    }

    override fun getAuthorities(): MutableSet<Role?>? {
        return roles
    }

    override fun getPassword(): String {
        return password!!
    }

    override fun isEnabled(): Boolean {
        return active
    }

    override fun getUsername(): String {
        return username!!
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    @ElementCollection(targetClass = Role::class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = [JoinColumn(name = "user_id")])
    @Enumerated(EnumType.STRING)
    var roles: MutableSet<Role?>? = null
}
