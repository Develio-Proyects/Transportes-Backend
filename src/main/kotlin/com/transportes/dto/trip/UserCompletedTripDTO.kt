package com.transportes.dto.trip

import com.transportes.domain.enums.StateTrip
import java.time.LocalDateTime

data class UserCompletedTripDTO(
    val id: String,
    val origin: String,
    val destination: String,
    val state: String,
    val departureDate: LocalDateTime,
    val cargoType: String
)