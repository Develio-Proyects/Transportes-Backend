package com.transportes.domain.viajes

import jakarta.persistence.*

@Entity @Table(name = "estados")
class Estado(
    @Column(nullable = false, unique = true)
    val nombre: String
) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
}