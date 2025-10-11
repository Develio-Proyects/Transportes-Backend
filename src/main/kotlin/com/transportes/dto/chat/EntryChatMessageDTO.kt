package com.transportes.dto.chat

import jakarta.validation.constraints.NotBlank

data class EntryChatMessageDTO(
    @field:NotBlank
    val transmitterId: String,
    @field:NotBlank
    var message: String
)