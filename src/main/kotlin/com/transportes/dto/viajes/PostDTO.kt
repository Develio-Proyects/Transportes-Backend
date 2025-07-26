package com.transportes.dto.viajes

import java.time.LocalDateTime

data class PostDTO(
    var id: String,
    var origen: String,
    var destino: String,
    var fechaSalida: LocalDateTime,
    var precioBase: Double,
    var cantidadPostulaciones: Long,
    var publicadoHace: String,
    var miPublicacion: Boolean
)