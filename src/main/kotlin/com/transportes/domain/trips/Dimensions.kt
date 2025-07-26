package com.transportes.domain.trips

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class Dimensions(
    @Column(nullable = false)
    val width: Double,
    @Column(nullable = false)
    val high: Double,
    @Column(nullable = false)
    val long: Double
)