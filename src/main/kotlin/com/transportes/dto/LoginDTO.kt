package com.transportes.dto

import jakarta.validation.constraints.NotBlank

data class LoginDTO (
    @field:NotBlank
    val email: String,
    @field:NotBlank
    val password: String
)