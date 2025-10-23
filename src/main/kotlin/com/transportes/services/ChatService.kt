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
        //message.message = censorMessage(message.message) // Se deja comanetado ya que no se utilizara la censura por el momento
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

    fun censorMessage(text: String): String {
        val emailRegex = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}", RegexOption.IGNORE_CASE)
        val phoneRegex = Regex("(\\+?\\d{1,3}[- .]?)?(\\(?\\d{2,4}\\)?[- .]?)?\\d{3,4}[- .]?\\d{3,4}", RegexOption.IGNORE_CASE)

        return text
            .replace(emailRegex) { "*".repeat(it.value.length) }
            .replace(phoneRegex) { "*".repeat(it.value.length) }
    }
}