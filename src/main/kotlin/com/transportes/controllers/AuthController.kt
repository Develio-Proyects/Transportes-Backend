package com.transportes.controllers

import com.transportes.dto.LoginDTO
import com.transportes.dto.ResponseWithMessageDTO
import com.transportes.dto.UpdatePassDTO
import com.transportes.services.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController {
    @Autowired private lateinit var authService: AuthService

    @PostMapping("/login")
    fun login(@RequestBody loginDTO: LoginDTO): ResponseEntity<Map<String, String>> {
        val token = authService.authenticate(loginDTO.username, loginDTO.password)
        return ResponseEntity.ok(mapOf("token" to token))
    }

    @PutMapping("/update-password")
    fun updatePassword(@RequestBody updatePassDTO: UpdatePassDTO): ResponseEntity<ResponseWithMessageDTO> {
        authService.updatePassword(
            updatePassDTO.username,
            updatePassDTO.oldPassword,
            updatePassDTO.newPassword
        )
        return ResponseEntity.ok().body(ResponseWithMessageDTO("Contraseña actualizada correctamente"))
    }

    @PostMapping("/validate-token")
    fun validateToken(@RequestHeader("Authorization") token: String): ResponseEntity<Map<String, Boolean>> {
        val isValid = authService.validateToken(token)
        return ResponseEntity.ok().body(mapOf("isValid" to isValid))
    }
}