package com.transportes.dto.login

data class LoginResponseDTO(
    val role: String,
    val name: String,
    val token: String
)