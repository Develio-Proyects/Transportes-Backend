package com.transportes.dto.trip

import com.transportes.domain.trips.Dimensions
import java.time.LocalDateTime

data class TripDetailDTO(
    val razonSocial: String,
    val fechaSalida: LocalDateTime,
    val estados: String,
    val origen: String,
    val destino: String,
    val observaciones: String?,
    val tipoDeCarga: String,
    val peso: Double,
    val dimensions: Dimensions,
    val precioInicial: Double,
    val ofertaMasBaja: Double?,
    val postulantes: List<OfferDTO>
)