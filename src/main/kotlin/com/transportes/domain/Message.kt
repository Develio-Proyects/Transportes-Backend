package com.transportes.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity @Table(name = "messages")
data class Message(
    @Column(nullable = false)
    val tripId: String,
    @Column(nullable = false)
    val transmitterId: String,
    @Column(nullable = false)
    val message: String,
    @Column(nullable = false)
    val timestamp: LocalDateTime = LocalDateTime.now()
) {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    lateinit var id: String
}