package com.transportes.utils

import com.transportes.domain.viajes.Viaje
import com.transportes.dto.ViajeDisponibleDTO

object Serializer {
    fun buildViajeDisponibleDTO(viaje: Viaje, cantPostulaciones: Long): ViajeDisponibleDTO {
        return ViajeDisponibleDTO(
            viaje.id,
            viaje.origen,
            viaje.destino,
            viaje.fechaSalida,
            viaje.precioBase,
            cantPostulaciones
        )
    }
}