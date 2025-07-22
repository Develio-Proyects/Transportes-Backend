package com.transportes.dto.viajes

import com.transportes.domain.enums.TipoCarga
import com.transportes.domain.viajes.Dimensiones
import java.time.LocalDateTime

data class NuevoViajeDTO(
    val origen: String,
    val destino: String,
    val fechaSalida: LocalDateTime,
    val precioBase: Double,
    val tipoCarga: TipoCarga,
    val peso: Double,
    val dimensiones: Dimensiones,
    val observaciones: String? = null
)