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

    @OneToMany(mappedBy = "author", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    private lateinit var messages: Set<Message>

    @ElementCollection(targetClass = Role::class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = [JoinColumn(name = "user_id")])
    @Enumerated(EnumType.STRING)
    var roles: MutableSet<Role?>? = null

    @ManyToMany
    @JoinTable(
            name = "user_subscriptions",
            joinColumns = [JoinColumn(name = "channel_id")],
            inverseJoinColumns = [JoinColumn(name = "subscriber_id")]
    )
    private lateinit var subscribers: Set<User>

    @ManyToMany
    @JoinTable(
            name = "user_subscriptions",
            joinColumns = [JoinColumn(name = "subscriber_id")],
            inverseJoinColumns = [JoinColumn(name = "channel_id")]
    )
    private lateinit var subscriptions: Set<User>

    @Email(message = "Email is not correct")
    @NotBlank(message = "Email cannot be empty")
    private var email: String? = null
    private var activationCode: String? = null

    fun getSubscribers(): Set<User> {
        return subscribers
    }

    fun setSubscribers(subscribers: Set<User>) {
        this.subscribers = subscribers
    }


    fun getSubscriptions(): Set<User> {
        return subscriptions
    }

    fun setSubscriptions(subscriptions: Set<User>) {
        this.subscriptions = subscriptions
    }

    fun getMessages(): Set<Message> {
        return messages
    }

    fun setMessages(messages: Set<Message>) {
        this.messages = messages
    }

    fun getEmail(): String? {
        return email
    }

    fun setEmail(value: String) {
        email = value
    }

    fun setPassword(value: String) {
        password = value
    }

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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
