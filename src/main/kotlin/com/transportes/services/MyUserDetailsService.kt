package com.transportes.services

import com.transportes.domain.usuarios.Usuario
import com.transportes.exceptions.InvalidCredentialsException
import com.transportes.repositories.UsuarioRepository
import com.transportes.utils.JwtUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class MyUserDetailsService : UserDetailsService {
    @Autowired private lateinit var userRepository: UsuarioRepository
    @Autowired lateinit var usuarioRepository: UsuarioRepository
    @Autowired lateinit var jwtUtils: JwtUtil

    override fun loadUserByUsername(email: String): UserDetails {
        val user = userRepository.findByEmail(email) ?: throw InvalidCredentialsException("Token Inválido")
        val role = "ROLE_${user.rol.uppercase()}"
        return User(
            user.email,
            user.password,
            listOf(SimpleGrantedAuthority(role))
        )
    }

    fun loadUserByToken(token: String): Usuario? {
        val email = jwtUtils.extractEmail(token.removePrefix("Bearer ").trim())
        return if (email != null) usuarioRepository.findByEmail(email)
        else throw InvalidCredentialsException("Token Inválido")
    }

    fun getCurrentUserEmail(): String? {
        val auth = org.springframework.security.core.context.SecurityContextHolder.getContext().authentication
        return if (auth != null && auth.isAuthenticated) {
            (auth.principal as User).username
        } else null
    }
}