package com.transportes.domain.usuarios

import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity @Table(name = "fleteros")
class Fletero(
    email: String,
    password: String,
) : Usuario(email, password, "FLETERO")