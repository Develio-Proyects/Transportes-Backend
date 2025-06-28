package com.transportes.utils

import com.transportes.domain.viajes.Postulacion
import com.transportes.domain.viajes.Viaje
import com.transportes.dto.LoginResponseDTO
import com.transportes.dto.ViajeDetalleDTO
import com.transportes.dto.ViajeDisponibleDTO


object Serializer {
    fun buildViajeDisponibleDTO(viaje: Viaje, cantPostulaciones: Long): ViajeDisponibleDTO {
        val publicadoHace: String = FechaUtils.tiempoTranscurrido(viaje.fechaPublicacion)
        return ViajeDisponibleDTO(
            viaje.id,
            viaje.origen,
            viaje.destino,
            viaje.fechaSalida,
            viaje.precioBase,
            cantPostulaciones,
            publicadoHace
        )
    }

    fun buildLoginResponseDTO(rol: String, token: String): LoginResponseDTO {
        return LoginResponseDTO(
            rol,
            token
        )
    }

    fun buildDetalleViajeDTO(viaje: Viaje, postulaciones: List<Postulacion>): ViajeDetalleDTO {
        val ofertaMasBaja = postulaciones.minByOrNull{ it.precioOfrecido }?.precioOfrecido
        return ViajeDetalleDTO(
            viaje.flota.razonSocial,
            viaje.fechaSalida,
            viaje.origen,
            viaje.destino,
            viaje.observaciones,
            viaje.tipoDeCarga,
            viaje.peso,
            viaje.dimensiones,
            viaje.precioBase,
            ofertaMasBaja,
            postulaciones.map { it.transporte.nombre }
        )
    }
}