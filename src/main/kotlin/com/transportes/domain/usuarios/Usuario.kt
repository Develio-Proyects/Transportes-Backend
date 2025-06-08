package com.transportes.domain.usuarios

import jakarta.persistence.*

@Entity @Table(name = "usuarios")
@Inheritance(strategy = InheritanceType.JOINED)
abstract class Usuario(
    @Column(nullable = false, unique = true)
    var email: String,
    @Column(nullable = false)
    var password: String,
    @Column(nullable = false)
    val rol: String
) {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    lateinit var id: String
}