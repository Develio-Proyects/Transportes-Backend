package com.transportes.dto.login

import jakarta.validation.constraints.NotBlank

data class UpdatePassDTO(
    @field:NotBlank
    val username: String,
    @field:NotBlank
    val oldPassword: String,
    @field:NotBlank
    val newPassword: String
)