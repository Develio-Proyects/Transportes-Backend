package com.transportes.domain.viajes

import com.fasterxml.jackson.annotation.JsonBackReference
import com.transportes.domain.usuarios.Transport
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity @Table(name = "offers")
class Offer(
    @ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "id_trip", nullable = false)
    @JsonBackReference // Evita referencia circular
    val trip: Trip,
    @ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "id_transport", nullable = false)
    val transport: Transport,
    @Column(nullable = false)
    val offeredPrice: Double,
    @Column(nullable = false)
    val date: LocalDateTime = LocalDateTime.now()
) {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    lateinit var id: String
}