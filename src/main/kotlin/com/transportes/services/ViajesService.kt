package com.transportes.services

import com.transportes.domain.usuarios.Flota
import com.transportes.domain.usuarios.Unipersonal
import com.transportes.domain.viajes.Viaje
import com.transportes.dto.DimensionesDTO
import com.transportes.dto.ViajeDetalleDTO
import com.transportes.dto.ViajeDisponibleDTO
import com.transportes.repositories.PostulacionRepository
import com.transportes.repositories.ViajeRepository
import com.transportes.utils.Serializer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ViajesService {
    @Autowired lateinit var viajesRepository: ViajeRepository
    @Autowired lateinit var postulacionesRepository: PostulacionRepository

    fun getViajesDisponibles(page: Int, size: Int): Page<ViajeDisponibleDTO> {
        val page: Pageable = Pageable.ofSize(size).withPage(page)
        val listaViajes = viajesRepository.getViajesDisponibles(page)
        return listaViajes.map {
            val cantPostulaciones = postulacionesRepository.getCantidadPostulacionesByViajeId(it.id)
            Serializer.buildViajeDisponibleDTO(it, cantPostulaciones)
        }
    }

    fun getDetalleViaje(id: String): ViajeDetalleDTO {
        val viaje:  Viaje = viajesRepository.findById(id).orElseThrow{
            RuntimeException("Viaje con id $id no fue encontrado o no existe")
        }

        val postulaciones = postulacionesRepository.findAllByViajeId(viaje.id)

        var ofertaMasBaja: Double? = null //devuelve el valor de la menor oferta
        for (postulacion in postulaciones) {
            if (ofertaMasBaja == null || postulacion.precioOfrecido < ofertaMasBaja) {
                ofertaMasBaja = postulacion.precioOfrecido
            }
        }

        val usuarioConOferta = mutableListOf<String>() //devuelve los postulantes ordenados de menor a mayor oferta
        val postulacionesOrdenadas = postulaciones.toMutableList()
        postulacionesOrdenadas.sortWith(compareBy { it.precioOfrecido })

        for (postulacion in postulacionesOrdenadas) { //si es unipersonal devuelve nombre - si es flota devuelve razon social
            val transporte = postulacion.transporte
            val nombreFletero = when (transporte) {
                is Flota -> transporte.razonSocial
                is Unipersonal -> "${transporte.nombre} ${transporte.apellido}"
                else -> "NN"
            }
            usuarioConOferta.add(nombreFletero)
        }


        return ViajeDetalleDTO( //datos del viaje
            razonSocial = viaje.flota.razonSocial,
            fechaSalida = viaje.fechaSalida.toLocalDate(),
            origen = viaje.origen,
            destino = viaje.destino,
            observaciones = viaje.observaciones,
            tipoDeCarga = viaje.tipoDeCarga,
            peso = viaje.peso,
            dimensiones = DimensionesDTO(
                ancho = viaje.dimensiones.ancho,
                alto = viaje.dimensiones.alto,
                largo = viaje.dimensiones.largo
            ),
            precioInicial = viaje.precioBase,
            ofertaMasBaja = ofertaMasBaja, //devuelve el valor, no el postulante
            usuarioConOferta = usuarioConOferta //devuelve postulaciones de menor a mayor
        )
    }
}