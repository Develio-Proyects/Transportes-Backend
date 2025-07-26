package com.transportes.dto.viajes

import com.transportes.domain.viajes.Dimensions
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
    val postulantes: List<PostulacionDTO>
)