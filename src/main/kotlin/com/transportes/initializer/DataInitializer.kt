package com.transportes.initializer

import com.transportes.domain.User
import com.transportes.repositories.UserRepository
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Repository

@Repository
class DataInitializer: InitializingBean {
    @Autowired lateinit var repositorioUser: UserRepository
    @Autowired lateinit var passwordEncoder: PasswordEncoder
    @Value("\${SPRING_PROFILES_ACTIVE}") lateinit var profile: String

    override fun afterPropertiesSet() {
        if (profile == "dev") {
            inicializarUser()
            println("Datos inicializados")
        }
    }

    fun inicializarUser() {
        val user = User("admin", passwordEncoder.encode("admin"))
        repositorioUser.save(user)
    }
}