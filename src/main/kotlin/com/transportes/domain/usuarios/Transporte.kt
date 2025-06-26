package com.transportes.domain.usuarios

import jakarta.persistence.*

@Entity @Table(name = "transportes")
@Inheritance(strategy = InheritanceType.JOINED)
abstract class Transporte(
    val nombre: String,
    email: String,
    password: String,
    rol: String
) : Usuario(email, password, rol)