package com.transportes.services

import com.transportes.dto.login.LoginResponseDTO
import com.transportes.exceptions.BadRequestException
import com.transportes.exceptions.InvalidCredentialsException
import com.transportes.exceptions.NotFoundException
import com.transportes.repositories.UsuarioRepository
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
    @Autowired private lateinit var authenticationManager: AuthenticationManager
    @Autowired private lateinit var userRepository: UsuarioRepository
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
        return Serializer.buildLoginResponseDTO(user.rol, user.nombre ,token)
    }

    fun updatePassword(email: String, oldPassword: String, newPassword: String) {
        val user = userRepository.findByEmail(email) ?: throw NotFoundException("Usuario no encontrado")

        if (newPassword.isEmpty() || newPassword.isBlank()) throw BadRequestException("La nueva contraseña no puede estar vacía")

        if (!passwordEncoder.matches(oldPassword, user.password)) {
            throw InvalidCredentialsException("La contraseña antigua es incorrecta")
        }

        user.password = passwordEncoder.encode(newPassword)
        userRepository.save(user)
    }

    fun validateToken(token: String): Boolean {
        val tokenWithoutBearer = token.removePrefix("Bearer ").trim()
        val username = jwtUtil.extractEmail(tokenWithoutBearer)
        return jwtUtil.validateToken(tokenWithoutBearer, username)
    }
}