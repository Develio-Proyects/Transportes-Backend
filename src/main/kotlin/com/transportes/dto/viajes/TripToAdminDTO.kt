package com.transportes.dto.viajes

import java.time.LocalDateTime

data class TripToAdminDTO(
    val origen: String,
    val destino: String,
    val fechaSalida: LocalDateTime,
    val precioBase: Double,
    val conductorAsignado: String?,
    val flota: String,
    val estado: String
)