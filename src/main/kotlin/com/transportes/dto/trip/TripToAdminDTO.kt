package com.transportes.dto.trip

import java.time.LocalDateTime

data class TripToAdminDTO(
    val origin: String,
    val destination: String,
    val departureDate: LocalDateTime,
    val basePrice: Double,
    val driverAssigned: String?,
    val multiCarrier: String,
    val state: String
)