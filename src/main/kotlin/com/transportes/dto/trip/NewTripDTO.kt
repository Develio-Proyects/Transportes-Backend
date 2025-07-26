package com.transportes.dto.trip

import com.transportes.domain.enums.CargoType
import com.transportes.domain.trips.Dimensions
import java.time.LocalDateTime

data class NewTripDTO(
    val origin: String,
    val destination: String,
    val departureDate: LocalDateTime,
    val basePrice: Double,
    val cargoType: CargoType,
    val weight: Double,
    val dimensions: Dimensions,
    val observations: String? = null
)