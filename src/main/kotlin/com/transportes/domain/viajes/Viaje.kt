package com.transportes.domain.viajes

import com.transportes.domain.usuarios.Flota
import com.transportes.domain.usuarios.Transporte
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity @Table(name = "viajes")
class Viaje(
    @ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "id_publicador", nullable = false)
    val flota: Flota,
    @ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "id_fletero_elegido", nullable = true)
    val transporteElegido: Transporte? = null,
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