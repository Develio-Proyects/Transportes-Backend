package com.transportes.domain.usuarios

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity @Table(name = "unipersonales")
class Unipersonal(
    email: String,
    password: String,
    nombre: String,
    @Column(nullable = false)
    val apellido: String,
    @Column(nullable = false)
    val cuil: Int
) : Transporte(nombre, email, password, "UNIPERSONAL")