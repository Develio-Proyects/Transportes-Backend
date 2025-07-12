package com.transportes.dto.viajes

import java.time.LocalDateTime

data class ViajeDTO(
    val id: String,
    val origen: String,
    val destino: String,
    val estado: String,
    val fechaSalida: LocalDateTime,
    val precioBase: Double,
    val cantidadPostulaciones: Long,
    val publicadoHace: String
)