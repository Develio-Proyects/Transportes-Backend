package com.transportes.dto.login

data class LoginResponseDTO(
    val userId: String,
    val name: String,
    val email: String,
    val role: String,
    val token: String
)