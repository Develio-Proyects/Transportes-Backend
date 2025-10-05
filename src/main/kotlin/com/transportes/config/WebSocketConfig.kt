package com.transportes.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer

@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfig : WebSocketMessageBrokerConfigurer {

    @Value("\${spring.url.front}") private lateinit var allowOrigins: String

    override fun configureMessageBroker(config: MessageBrokerRegistry) {
        config.enableSimpleBroker("/topic") // Destino a donde se mandan los mensajes
        config.setApplicationDestinationPrefixes("/app") // Prefijo para recibir mensajes
    }

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        val list = allowOrigins.split(",").map { it.trim() }
        registry
            .addEndpoint("/ws-chat") // Endpoint para conectar
            .setAllowedOrigins(*list.toTypedArray())
            .withSockJS()
    }
}