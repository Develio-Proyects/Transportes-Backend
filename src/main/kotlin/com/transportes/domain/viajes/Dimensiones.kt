package com.transportes.domain.viajes

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class Dimensiones(
    @Column(nullable = false)
    val ancho: Double,
    @Column(nullable = false)
    val alto: Double,
    @Column(nullable = false)
    val largo: Double
)