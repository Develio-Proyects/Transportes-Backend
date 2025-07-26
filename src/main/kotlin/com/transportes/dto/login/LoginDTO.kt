package com.transportes.dto.login

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class LoginDTO (
    @field:NotBlank
    @field:Email
    val email: String,
    @field:NotBlank
    val password: String
)