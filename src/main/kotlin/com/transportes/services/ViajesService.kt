package com.transportes.services

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
}