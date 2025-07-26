package com.transportes.controllers

import com.transportes.dto.*
import com.transportes.dto.login.LoginDTO
import com.transportes.dto.login.LoginResponseDTO
import com.transportes.dto.login.UpdatePassDTO
import com.transportes.services.AuthService
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController {
    @Autowired private lateinit var authService: AuthService

    @PostMapping("/login")
    @Operation(
        summary = "Login",
        description = "Endpoint for user login. Returns a JWT token."
    )
    fun login(@RequestBody @Valid loginDTO: LoginDTO): LoginResponseDTO {
        return authService.authenticate(loginDTO.email, loginDTO.password)
    }

    @PutMapping("/update-password")
    @Operation(
        summary = "Update password",
        description = "Allows the user to update their password by providing the old and new password."
    )
    fun updatePassword(@RequestBody @Valid updatePassDTO: UpdatePassDTO): ResponseEntity<ResponseWithMessageDTO> {
        authService.updatePassword(
            updatePassDTO.oldPassword,
            updatePassDTO.newPassword
        )
        return ResponseEntity.ok( ResponseWithMessageDTO("Contraseña actualizada correctamente") )
    }

    @PostMapping("/validate-token")
    @Operation(
        summary = "Validate JWT token",
        description = "Checks if the JWT token provided in the Authorization header is valid."
    )
    fun validateToken(@RequestHeader("Authorization") token: String): ResponseEntity<Map<String, Boolean>> {
        val isValid = authService.validateToken(token)
        return ResponseEntity.ok( mapOf("isValid" to isValid) )
    }
}