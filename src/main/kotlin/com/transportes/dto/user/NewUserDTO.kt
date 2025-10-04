package com.transportes.dto.user

import com.transportes.domain.enums.Role
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class NewUserDTO(
    val name: String?,
    val lastname: String?,
    val documentNumber: Int?,
    @field:Email
    val email: String,
    @field:NotBlank
    val password: String,
    val role: Role
)