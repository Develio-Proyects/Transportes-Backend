package com.transportes.dto

import com.transportes.domain.viajes.Dimensiones
import java.time.LocalDateTime

data class ViajeDetalleDTO(
    val razonSocial: String,
    val fechaSalida: LocalDateTime,
    val estados: String,
    val origen: String,
    val destino: String,
    val observaciones: String?,
    val tipoDeCarga: String,
    val peso: Double,
    val dimensiones: Dimensiones,
    val precioInicial: Double,
    val ofertaMasBaja: Double?,
    val postulantes: List<PostulacionDTO>
)