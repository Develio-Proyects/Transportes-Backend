package com.transportes.dto.vehiculo

data class VehiculoDTO(
    val id: String,
    var marca: String,
    val modelo: String,
    val patente: String,
    val urlTituloCamion: String,
    val urlTituloSemi: String
)