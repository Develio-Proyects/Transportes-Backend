package com.transportes.services

import com.transportes.exceptions.InvalidCredentialsException
import com.transportes.repositories.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class MyUserDetailsService : UserDetailsService {
    @Autowired private lateinit var userRepository: UsuarioRepository

    override fun loadUserByUsername(email: String): UserDetails {
        val user = userRepository.findByEmail(email) ?: throw InvalidCredentialsException("Token Inválido")
        return User(
            user.email,
            user.password,
            listOf()
        )
    }
}