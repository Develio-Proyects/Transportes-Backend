package com.transportes.controllers

import com.transportes.dto.chat.EntryChatMessageDTO
import com.transportes.services.ChatService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller

@Controller
class WSController {

    @Autowired lateinit var chatService: ChatService

    @MessageMapping("/chat.send.{tripId}")
    fun sendMessage(
        @DestinationVariable tripId: String,
        @Valid message: EntryChatMessageDTO
    ) {
        if ( chatService.userCanChat(tripId) ) {
            chatService.sendMessage(tripId, message)
        }
    }
}