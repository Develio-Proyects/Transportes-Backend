package com.transportes.services

import com.transportes.domain.Message
import com.transportes.dto.chat.EntryChatMessageDTO
import com.transportes.dto.chat.ExitChatMessageDTO
import com.transportes.repositories.MessageRepository
import com.transportes.utils.Serializer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service

@Service
class ChatService {

    @Autowired lateinit var messagingTemplate: SimpMessagingTemplate
    @Autowired lateinit var messageRepository: MessageRepository

    // TODO: Verificar que este loguado y sea el publicador o el elegido
    fun userCanChat(tripId: String): Boolean {
        return true
    }

    fun sendMessage(tripId: String, message: EntryChatMessageDTO) {
        saveMessage(tripId, message)
        messagingTemplate.convertAndSend("/topic/chat/trip/$tripId", message)
    }

    private fun saveMessage(tripId: String, message: EntryChatMessageDTO) {
        val newMessage = Message(
            tripId = tripId,
            transmitterId = message.transmitterId,
            message = message.message
        )
        messageRepository.save(newMessage)
    }

    // TODO: Validar que el usuario sea el publicador o el elegido
    fun getMessagesByTripId(tripId: String): List<ExitChatMessageDTO> {
        return messageRepository.findByTripId(tripId).map {
            Serializer.buildExitChatMessageDTOByMessage(it)
        }
    }
}