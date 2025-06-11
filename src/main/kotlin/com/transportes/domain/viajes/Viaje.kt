package com.transportes.domain.viajes

import com.transportes.domain.usuarios.Fletero
import com.transportes.domain.usuarios.Transporte
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity @Table(name = "viajes")
class Viaje(
    @ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "id_transporte", nullable = false)
    val transporte: Transporte,
    @ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "id_fletero_elegido")
    val fleteroElegido: Fletero? = null,
    @ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "id_estado", nullable = false)
    val estado: Estado,
    @Column(nullable = false)
    val origen: String,
    @Column(nullable = false)
    val destino: String,
    @Column(nullable = false)
    val fechaSalida: LocalDateTime,
    @Column(nullable = false)
    val precioBase: Double,
    @Column
    val observaciones: String? = null
) {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    lateinit var id: String
}