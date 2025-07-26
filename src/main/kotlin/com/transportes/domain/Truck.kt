package com.transportes.domain

import com.transportes.domain.usuarios.Transport
import jakarta.persistence.*

@Entity @Table(name = "trucks")
class Truck(
    @Column(nullable = false)
    val brand: String,
    @Column(nullable = false)
    val model: String,
    @Column(nullable = false)
    val patent: String,
    @ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "id_transport", nullable = false)
    val transport: Transport
) {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    lateinit var id: String
}