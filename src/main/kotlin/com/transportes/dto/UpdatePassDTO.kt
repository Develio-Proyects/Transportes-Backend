package com.transportes.dto

data class UpdatePassDTO(
    val username: String,
    val oldPassword: String,
    val newPassword: String
)