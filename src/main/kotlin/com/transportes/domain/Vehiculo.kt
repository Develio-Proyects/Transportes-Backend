package com.transportes.domain

import com.transportes.domain.usuarios.Transporte
import jakarta.persistence.*

@Entity @Table(name = "vehiculos")
class Vehiculo(
    @Column(nullable = false)
    val marca: String,
    @ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "id_transporte", nullable = false)
    val transporte: Transporte
) {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    lateinit var id: String
}