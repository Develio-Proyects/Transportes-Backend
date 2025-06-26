package com.transportes.domain.usuarios

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity @Table(name = "flotas")
class Flota(
    email: String,
    password: String,
    @Column(nullable = false)
    val razonSocial: String,
    @Column(nullable = false)
    val cuit: Int
): Transporte(razonSocial, email, password, "FLOTA")