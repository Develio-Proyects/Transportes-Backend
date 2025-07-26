package com.transportes.domain.usuarios

import com.transportes.domain.enums.Role
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity @Table(name = "solo_carriers")
class SoloCarrier(
    email: String,
    password: String,
    name: String,
    @Column(nullable = false)
    val lastName: String,
    @Column(nullable = false)
    val documentNumber: Int
) : Transport("$name $lastName", email, password, Role.SOLO_CARRIER)