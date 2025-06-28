package com.transportes.dto

import java.time.LocalDateTime

data class ViajeDisponibleDTO(
    var id: String,
    var origen: String,
    var destino: String,
    var fechaSalida: LocalDateTime,
    var precioBase: Double,
    var cantidadPostulaciones: Long,
    var publicadoHace: String,
    var miPublicacion: Boolean
)