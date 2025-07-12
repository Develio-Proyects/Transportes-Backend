package com.transportes.domain.usuarios

import jakarta.persistence.*

@Entity @Table(name = "administradores")
class Administrador(
    email: String,
    password: String
) : Usuario(email, password, "Administrador", "ADMINISTRADOR")