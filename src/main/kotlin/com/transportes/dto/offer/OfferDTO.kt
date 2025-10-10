package com.transportes.dto.offer

data class OfferDTO(
    val id: String,
    val name: String,
    val userId: String,
    val offeredPrice: Double
)