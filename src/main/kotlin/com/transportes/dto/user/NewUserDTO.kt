package com.transportes.dto.user

import com.transportes.domain.enums.Role

data class NewUserDTO(
    val name: String,
    val lastname: String?,
    val documentNumber: Long,
    val email: String,
    val password: String,
    val role: Role
)