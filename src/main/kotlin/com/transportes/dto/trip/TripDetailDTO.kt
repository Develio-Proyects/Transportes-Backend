package com.transportes.dto.trip

import com.transportes.domain.trips.Dimensions
import java.time.LocalDateTime

data class TripDetailDTO(
    val companyName: String,
    val departureDate: LocalDateTime,
    val state: String,
    val origin: String,
    val destination: String,
    val observations: String?,
    val cargoType: String,
    val weight: Double,
    val dimensions: Dimensions,
    val initialPrice: Double,
    val lowerOffer: Double?,
    val offers: List<OfferDTO>
)