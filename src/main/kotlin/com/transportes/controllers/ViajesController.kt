package com.transportes.controllers

import com.transportes.dto.ViajeDisponibleDTO
import com.transportes.dto.PaginadoDTO
import com.transportes.services.ViajesService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/viajes")
class ViajesController {
    @Autowired lateinit var viajesService: ViajesService

    @GetMapping("/disponibles")
    fun getViajesDisponibles(pageable: Pageable): PaginadoDTO<ViajeDisponibleDTO> {
        val page = viajesService.getViajesDisponibles(pageable)
        return PaginadoDTO(
            page.content,
            page.totalElements,
            page.totalPages,
            page.size
        )
    }
}