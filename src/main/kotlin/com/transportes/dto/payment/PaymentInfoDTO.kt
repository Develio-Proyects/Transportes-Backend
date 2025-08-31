package com.transportes.dto.payment

data class PaymentInfoDTO(
    val publisher: String,
    val transport: String,
    val origin: String,
    val destination: String,
    val mount: Double
)