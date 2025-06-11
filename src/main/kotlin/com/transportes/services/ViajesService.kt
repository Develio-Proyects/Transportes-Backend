package com.transportes.services

import com.transportes.dto.ViajeDisponibleDTO
import com.transportes.repositories.ViajeRepository
import com.transportes.utils.Serializer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ViajesService {
    @Autowired lateinit var viajesRepository: ViajeRepository

    fun getViajesDisponibles(pageable: Pageable): Page<ViajeDisponibleDTO> {
        val viajesPage = viajesRepository.getViajesDisponibles(pageable)
        return viajesPage.map { Serializer.buildViajeDisponibleDTO(it) }
    }
}