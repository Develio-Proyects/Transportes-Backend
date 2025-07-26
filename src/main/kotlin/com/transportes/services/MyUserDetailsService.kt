package com.transportes.services

import com.transportes.domain.usuarios.User
import com.transportes.exceptions.InvalidCredentialsException
import com.transportes.repositories.UserRepository
import com.transportes.utils.JwtUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User as SpringUser
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class MyUserDetailsService : UserDetailsService {
    @Autowired lateinit var userRepository: UserRepository
    @Autowired lateinit var jwtUtils: JwtUtil

    override fun loadUserByUsername(email: String): UserDetails {
        val user = userRepository.findByEmail(email) ?: throw InvalidCredentialsException("Token Inválido")
        val role = "ROLE_${user.role.frontName.uppercase()}"
        return SpringUser(
            user.email,
            user.password,
            listOf(SimpleGrantedAuthority(role))
        )
    }

    fun getUserByToken(token: String): User? {
        val email = jwtUtils.extractEmail(token.removePrefix("Bearer ").trim())
        return if (email != null) userRepository.findByEmail(email)
        else throw InvalidCredentialsException("Token Inválido")
    }

    fun getCurrentUser(): User? {
        val auth = org.springframework.security.core.context.SecurityContextHolder.getContext().authentication
        lateinit var email: String
        return if (auth != null && auth.isAuthenticated) {
            try { email = (auth.principal as SpringUser).username }
            catch (e: ClassCastException) { throw InvalidCredentialsException("Token Inválido") }
            userRepository.findByEmail(email)
        } else null
    }
}