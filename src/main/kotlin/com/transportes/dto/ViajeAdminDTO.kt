package com.transportes.dto

import java.time.LocalDateTime

data class ViajeAdminDTO(
    val origen: String,
    val destino: String,
    val fechaSalida: LocalDateTime,
    val precioBase: Double,
    val conductorAsignado: String?,
    val flota: String,
    val estado: String
)