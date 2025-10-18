package com.transportes.dto.trip

import java.time.LocalDateTime

data class PostDTO(
    var id: String,
    var origin: String,
    var destination: String,
    var companyName: String,
    var departureDate: LocalDateTime,
    val cargoType: String,
    var basePrice: Double,
    var offersCount: Long,
    var postedSince: String,
    var myPost: Boolean
)