package com.transportes.domain.usuarios

import jakarta.persistence.Entity
import jakarta.persistence.Inheritance
import jakarta.persistence.InheritanceType
import jakarta.persistence.Table

@Entity @Table(name = "transportes")
@Inheritance(strategy = InheritanceType.JOINED)
abstract class Transporte(
    email: String,
    password: String,
    rol: String
) : Usuario(email, password, rol)