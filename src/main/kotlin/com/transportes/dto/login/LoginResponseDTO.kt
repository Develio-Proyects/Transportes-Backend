package com.transportes.dto.login

data class LoginResponseDTO(
    val rol: String,
    val nombre: String,
    val token: String
)