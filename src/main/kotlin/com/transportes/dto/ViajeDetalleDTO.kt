package com.transportes.dto

import java.time.LocalDate

data class ViajeDetalleDTO(
    val razonSocial: String,
    val fechaSalida: LocalDate,
    val origen: String,
    val destino: String,
    val observaciones: String?,
    val tipoDeCarga: String,
    val peso: Double,
    val dimensiones: DimensionesDTO,
    val precioInicial: Double,
    val ofertaMasBaja: Double?,
    val usuarioConOferta: List<String>
)
