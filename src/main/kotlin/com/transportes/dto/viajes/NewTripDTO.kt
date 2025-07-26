package com.transportes.dto.viajes

import com.transportes.domain.enums.CargoType
import com.transportes.domain.viajes.Dimensions
import java.time.LocalDateTime

data class NewTripDTO(
    val origen: String,
    val destino: String,
    val fechaSalida: LocalDateTime,
    val precioBase: Double,
    val cargoType: CargoType,
    val peso: Double,
    val dimensions: Dimensions,
    val observaciones: String? = null
)