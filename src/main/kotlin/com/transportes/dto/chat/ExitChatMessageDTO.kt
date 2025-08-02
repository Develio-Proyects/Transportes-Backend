package com.transportes.dto.chat

import java.time.LocalDateTime

data class ExitChatMessageDTO(
    val transmitterId: String,
    val time: LocalDateTime,
    val message: String
)
