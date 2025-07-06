package com.transportes.utils

import com.transportes.domain.viajes.Postulacion
import com.transportes.domain.viajes.Viaje
import com.transportes.dto.*


object Serializer {
    fun buildViajeDisponibleDTO(viaje: Viaje, cantPostulaciones: Long, miPublicacion: Boolean): ViajeDisponibleDTO {
        val publicadoHace: String = FechaUtils.tiempoTranscurrido(viaje.fechaPublicacion)
        return ViajeDisponibleDTO(
            viaje.id,
            viaje.origen,
            viaje.destino,
            viaje.fechaSalida,
            viaje.precioBase,
            cantPostulaciones,
            publicadoHace,
            miPublicacion
        )
    }

    fun buildViajePublicadoDTO(viaje: Viaje, cantPostulaciones: Long): ViajePublicadoDTO {
        val publicadoHace: String = FechaUtils.tiempoTranscurrido(viaje.fechaPublicacion)
        return ViajePublicadoDTO(
            viaje.id,
            viaje.origen,
            viaje.destino,
            viaje.estado.nombre.frontName,
            viaje.fechaSalida,
            viaje.precioBase,
            cantPostulaciones,
            publicadoHace
        )
    }

    fun buildViajeAdminDTO(viaje: Viaje): ViajeAdminDTO {
        return ViajeAdminDTO(
            viaje.origen,
            viaje.destino,
            viaje.fechaSalida,
            viaje.precioBase,
            viaje.postulacionElegida?.transporte?.nombre,
            viaje.flota.razonSocial,
            viaje.estado.nombre.frontName
        )
    }

    fun buildLoginResponseDTO(rol: String, token: String): LoginResponseDTO {
        return LoginResponseDTO(
            rol,
            token
        )
    }

    fun buildPostulacionDTO(postulacion: Postulacion): PostulacionDTO {
        return PostulacionDTO(
            postulacion.id,
            postulacion.transporte.nombre,
            postulacion.precioOfrecido
        )
    }

    fun buildDetalleViajeDTO(viaje: Viaje, postulaciones: List<Postulacion>): ViajeDetalleDTO {
        val ofertaMasBaja = postulaciones.minByOrNull{ it.precioOfrecido }?.precioOfrecido
        val listaPostulacionesDTO = postulaciones.map { buildPostulacionDTO(it) }
        return ViajeDetalleDTO(
            viaje.flota.razonSocial,
            viaje.fechaSalida,
            viaje.estado.nombre.frontName,
            viaje.origen,
            viaje.destino,
            viaje.observaciones,
            viaje.tipoDeCarga,
            viaje.peso,
            viaje.dimensiones,
            viaje.precioBase,
            ofertaMasBaja,
            listaPostulacionesDTO
        )
    }
}