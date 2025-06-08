package com.transportes.domain.usuarios

import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity @Table(name = "transportes")
class Transporte(
    email: String,
    password: String,
) : Usuario(email, password, "TRANSPORTE")