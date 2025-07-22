package com.transportes.domain.viajes

import com.fasterxml.jackson.annotation.JsonManagedReference
import com.transportes.domain.enums.EstadosViaje
import com.transportes.domain.enums.TipoCarga
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
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val estado: EstadosViaje,
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
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val tipoDeCarga: TipoCarga,
    @Column(nullable = false)
    val peso: Double,
    @Embedded
    val dimensiones: Dimensiones,
    @Column(nullable = false)
    val observaciones: String? = null
) {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    lateinit var id: String
}