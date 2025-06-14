package com.transportes.domain.usuarios

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity @Table(name = "unipersonales")
class Unipersonal(
    email: String,
    password: String,
    @Column(nullable = false)
    val nombre: String,
    @Column(nullable = false)
    val apellido: String,
    @Column(nullable = false)
    val cuil: Int
) : Transporte(email, password, "UNIPERSONAL")