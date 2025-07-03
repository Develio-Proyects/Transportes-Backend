package com.transportes.domain.viajes

import com.fasterxml.jackson.annotation.JsonManagedReference
import com.transportes.domain.usuarios.Flota
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity @Table(name = "viajes")
class Viaje(
    @ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "id_publicador", nullable = false)
    val flota: Flota,
    @OneToOne(fetch = FetchType.EAGER) @JoinColumn(name = "id_postulacion_elegida", nullable = true)
    @JsonManagedReference // Evita referencia circular
    var postulacionElegida: Postulacion? = null,
    @ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "id_estado", nullable = false)
    val estado: Estado,
    @Column(nullable = false)
    val origen: String,
    @Column(nullable = false)
    val destino: String,
    @Column(nullable = false)
    val fechaSalida: LocalDateTime,
    @Column(nullable = false)
    val fechaPublicacion: LocalDateTime,
    @Column(nullable = false)
    val precioBase: Double,
    @Column
    val tipoDeCarga: String,
    @Column
    val peso: Double,
    @Embedded
    val dimensiones: Dimensiones,
    @Column
    val observaciones: String? = null
) {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    lateinit var id: String
}