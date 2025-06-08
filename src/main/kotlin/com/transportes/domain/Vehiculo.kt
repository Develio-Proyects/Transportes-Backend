package com.transportes.domain

import com.transportes.domain.usuarios.Fletero
import jakarta.persistence.*

@Entity @Table(name = "vehiculos")
class Vehiculo(
    @Column(nullable = false)
    val marca: String,
    @ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "id_fletero", nullable = false)
    val fletero: Fletero
) {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    lateinit var id: String
}