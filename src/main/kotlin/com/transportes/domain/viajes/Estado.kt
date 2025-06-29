package com.transportes.domain.viajes

import com.transportes.domain.enums.EstadosViaje
import jakarta.persistence.*

@Entity @Table(name = "estados")
class Estado(
    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    val nombre: EstadosViaje
) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
}