package com.transportes.domain.viajes

import com.fasterxml.jackson.annotation.JsonBackReference
import com.transportes.domain.usuarios.Transporte
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity @Table(name = "postulaciones")
class Postulacion(
    @ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "id_viaje", nullable = false)
    @JsonBackReference // Evita referencia circular
    val viaje: Viaje,
    @ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "id_transporte", nullable = false)
    val transporte: Transporte,
    @Column(nullable = false)
    val precioOfrecido: Double,
    @Column(nullable = false)
    val fecha: LocalDateTime = LocalDateTime.now()
) {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    lateinit var id: String
}