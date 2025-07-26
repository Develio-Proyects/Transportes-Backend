package com.transportes.dto.trip

import java.time.LocalDateTime

data class TripDTO(
    val id: String,
    val origin: String,
    val destination: String,
    val state: String,
    val departureDate: LocalDateTime,
    val basePrice: Double,
    val offersCount: Long,
    val postedSince: String
)