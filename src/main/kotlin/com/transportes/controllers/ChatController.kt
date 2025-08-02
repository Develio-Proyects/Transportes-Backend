package com.transportes.controllers

import com.transportes.dto.chat.ExitChatMessageDTO
import com.transportes.services.ChatService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/chat")
class ChatController {

    @Autowired lateinit var chatService: ChatService

    @GetMapping("/{tripId}")
    @Operation(
        summary = "Get trip history chats",
        description = "Retrieve all messages for a specific trip"
    )
    fun getMessagesByTripId(
        @PathVariable tripId: String
    ): List<ExitChatMessageDTO> {
        return chatService.getMessagesByTripId(tripId)
    }
}