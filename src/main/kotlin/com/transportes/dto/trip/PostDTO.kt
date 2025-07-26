package com.transportes.dto.trip

import java.time.LocalDateTime

data class PostDTO(
    var id: String,
    var origin: String,
    var destination: String,
    var departureDate: LocalDateTime,
    var basePrice: Double,
    var offersCount: Long,
    var postedSince: String,
    var myPost: Boolean
)