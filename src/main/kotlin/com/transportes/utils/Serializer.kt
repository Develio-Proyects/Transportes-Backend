package com.transportes.utils

import com.transportes.domain.Vehiculo
import com.transportes.domain.documentos.Documento
import com.transportes.domain.enums.EstadosViaje
import com.transportes.domain.usuarios.Flota
import com.transportes.domain.viajes.Postulacion
import com.transportes.domain.viajes.Viaje
import com.transportes.dto.DocumentDTO
import com.transportes.dto.login.LoginResponseDTO
import com.transportes.dto.vehiculo.VehiculoDTO
import com.transportes.dto.viajes.*
import java.time.LocalDateTime

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

    fun buildViajeDTO(viaje: Viaje, cantPostulaciones: Long): ViajeDTO {
        val publicadoHace: String = FechaUtils.tiempoTranscurrido(viaje.fechaPublicacion)
        return ViajeDTO(
            viaje.id,
            viaje.origen,
            viaje.destino,
            viaje.estado.frontName,
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
            viaje.estado.frontName
        )
    }

    fun buildLoginResponseDTO(rol: String, nombre: String, token: String): LoginResponseDTO {
        return LoginResponseDTO(
            rol,
            nombre,
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
            viaje.estado.frontName,
            viaje.origen,
            viaje.destino,
            viaje.observaciones,
            viaje.tipoDeCarga.frontName,
            viaje.peso,
            viaje.dimensiones,
            viaje.precioBase,
            ofertaMasBaja,
            listaPostulacionesDTO
        )
    }

    fun buildVehiculoDTO(vehiculo: Vehiculo): VehiculoDTO {
        return VehiculoDTO(
            vehiculo.id,
            vehiculo.marca,
            vehiculo.modelo,
            vehiculo.patente
        )
    }

    fun buildDocumentDTO(document: Documento): DocumentDTO {
        return DocumentDTO(
            document.getIdUser(),
            document.nombre,
            document.linkArchivo
        )
    }

    fun buildViajeByNuevoViajeDTO(flota: Flota, viaje: NuevoViajeDTO): Viaje {
        return Viaje(
            flota = flota,
            postulacionElegida = null,
            estado = EstadosViaje.SUBASTA,
            origen = viaje.origen,
            destino = viaje.destino,
            fechaSalida = viaje.fechaSalida,
            fechaPublicacion = LocalDateTime.now(),
            precioBase = viaje.precioBase,
            tipoDeCarga = viaje.tipoCarga,
            peso = viaje.peso,
            dimensiones = viaje.dimensiones,
            observaciones = viaje.observaciones
        )
    }
}