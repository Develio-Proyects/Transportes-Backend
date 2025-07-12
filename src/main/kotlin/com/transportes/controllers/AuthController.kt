package com.transportes.controllers

import com.transportes.dto.*
import com.transportes.dto.login.LoginDTO
import com.transportes.dto.login.LoginResponseDTO
import com.transportes.dto.login.UpdatePassDTO
import com.transportes.services.AuthService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController {
    @Autowired private lateinit var authService: AuthService

    @PostMapping("/login")
    fun login(@RequestBody @Valid loginDTO: LoginDTO): LoginResponseDTO {
        return authService.authenticate(loginDTO.email, loginDTO.password)
    }

    @PutMapping("/update-password")
    fun updatePassword(@RequestBody @Valid updatePassDTO: UpdatePassDTO): ResponseEntity<ResponseWithMessageDTO> {
        authService.updatePassword(
            updatePassDTO.username,
            updatePassDTO.oldPassword,
            updatePassDTO.newPassword
        )
        return ResponseEntity.ok( ResponseWithMessageDTO("Contraseña actualizada correctamente") )
    }

    @PostMapping("/validate-token")
    fun validateToken(@RequestHeader("Authorization") token: String): ResponseEntity<Map<String, Boolean>> {
        val isValid = authService.validateToken(token)
        return ResponseEntity.ok( mapOf("isValid" to isValid) )
    }
}