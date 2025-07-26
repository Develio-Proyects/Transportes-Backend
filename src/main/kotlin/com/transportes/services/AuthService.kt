package com.transportes.services

import com.transportes.dto.login.LoginResponseDTO
import com.transportes.exceptions.BadRequestException
import com.transportes.exceptions.InvalidCredentialsException
import com.transportes.exceptions.NotFoundException
import com.transportes.repositories.UserRepository
import com.transportes.utils.JwtUtil
import com.transportes.utils.Serializer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService {
    @Autowired private lateinit var myUserDetailsService: MyUserDetailsService
    @Autowired private lateinit var authenticationManager: AuthenticationManager
    @Autowired private lateinit var userRepository: UserRepository
    @Autowired private lateinit var jwtUtil: JwtUtil
    @Autowired private lateinit var passwordEncoder: PasswordEncoder

    fun authenticate(email: String, password: String): LoginResponseDTO {
        try {
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(email, password)
            )
        } catch (e: AuthenticationException) { throw InvalidCredentialsException("Credenciales inválidas") }
        val user = userRepository.findByEmail(email) ?: throw NotFoundException("Usuario no encontrado")
        val token = jwtUtil.generateToken(user.email)
        return Serializer.buildLoginResponseDTO(user.role.frontName, user.name ,token)
    }

    fun updatePassword(oldPassword: String, newPassword: String) {
        val user = myUserDetailsService.getCurrentUser() ?: throw NotFoundException("Usuario no encontrado")
        if ( ! passwordEncoder.matches(oldPassword, user.password) ) throw BadRequestException("La contraseña antigua es incorrecta")
        user.password = passwordEncoder.encode(newPassword)
        userRepository.save(user)
    }

    fun validateToken(token: String): Boolean {
        val tokenWithoutBearer = token.removePrefix("Bearer ").trim()
        val username = jwtUtil.extractEmail(tokenWithoutBearer)
        return jwtUtil.validateToken(tokenWithoutBearer, username)
    }
}