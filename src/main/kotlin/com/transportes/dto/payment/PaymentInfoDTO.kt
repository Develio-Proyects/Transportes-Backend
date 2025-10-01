package com.transportes.dto.payment

import java.math.BigDecimal

data class PaymentInfoDTO(
    val publisher: String,
    val transport: String,
    val origin: String,
    val destination: String,
    val mount: BigDecimal
)