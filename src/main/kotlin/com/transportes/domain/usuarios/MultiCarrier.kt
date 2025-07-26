package com.transportes.domain.usuarios

import com.transportes.domain.enums.Role
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity @Table(name = "multi_carriers")
class MultiCarrier(
    email: String,
    password: String,
    @Column(nullable = false)
    val razonSocial: String,
    @Column(nullable = false)
    val documentNumber: Int
): Transport(razonSocial, email, password, Role.MULTI_CARRIER)