package ru.kotlyarov.spring.application.domain

import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank


@Entity
@Table(name = "usr")
class User(
        @field:NotBlank(message = "Username cannot be empty")
        private var username: String?,
        @field:NotBlank(message = "Password cannot be empty")
        private var password: String?,
        private var active: Boolean = true,
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long = 0) : UserDetails {

    fun setPassword(value: String) {
        password = value
    }

    @Transient
    @NotBlank(message = "Password confirmation cannot be empty")
    private var password2: String? = null

    @Email(message = "Email is not correct")
    @NotBlank(message = "Email cannot be empty")
    private var email: String? = null

    fun getEmail(): String? {
        return email
    }

    fun setEmail(value: String) {
        email = value
    }

    private var activationCode: String? = null

    fun getActivationCod(): String? {
        return activationCode
    }

    fun setActivationCod(value: String?) {
        activationCode = value
    }

    fun isUseActivateCode(): Boolean {
        return activationCode.isNullOrEmpty()
    }


    fun isAdmin(): Boolean {
        return roles!!.contains(Role.ADMIN)
    }

    fun setUsername(username: String) {
        this.username = username
    }

    override fun getAuthorities(): MutableSet<Role?>? {
        return roles
    }

    override fun getPassword(): String? {
        return password
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

    fun getPassword2(): String? {
        return password2
    }

    fun setPassword2(value: String) {
        password2 = value
    }

    @ElementCollection(targetClass = Role::class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = [JoinColumn(name = "user_id")])
    @Enumerated(EnumType.STRING)
    var roles: MutableSet<Role?>? = null
}
