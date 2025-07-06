package com.transportes.controllers

import com.transportes.dto.ViajeDisponibleDTO
import com.transportes.dto.PaginadoDTO
import com.transportes.dto.ViajeAdminDTO
import com.transportes.dto.ViajePublicadoDTO
import com.transportes.services.ViajesService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/viajes")
class ViajesController {
    @Autowired lateinit var viajesService: ViajesService

    @GetMapping("/disponibles")
    fun getViajesDisponibles(
        @RequestParam page: Int,
        @RequestParam size: Int,
        @RequestHeader("Authorization", required = false) token: String?
    ): PaginadoDTO<ViajeDisponibleDTO> {
        val viaje = viajesService.getViajesDisponibles(token, page, size)
        return PaginadoDTO(
            viaje.content,
            viaje.totalElements,
            viaje.totalPages,
            viaje.size
        )
    }

    @GetMapping("/publicaciones")
    fun getMisPublicaciones(
        @RequestParam page: Int,
        @RequestParam size: Int
    ): PaginadoDTO<ViajePublicadoDTO> {
        val page = viajesService.getMisPublicaciones(page, size)
        return PaginadoDTO(
            page.content,
            page.totalElements,
            page.totalPages,
            page.size
        )
    }

    @GetMapping("/{id}")
    fun getDetalleViaje(@PathVariable id: String) = viajesService.getDetalleViaje(id)

    @GetMapping("/postulacion/tarifa/{id}")
    fun getTarifaViaje(@PathVariable id: String): ResponseEntity<Map<String, Double>> {
        val tarifa = viajesService.getTarifaViaje(id)
        return ResponseEntity.ok( mapOf("tarifa" to tarifa) )
    }

    @GetMapping("/admin")
    fun getViajesAdmin(
        @RequestParam page: Int,
        @RequestParam size: Int,
    ): PaginadoDTO<ViajeAdminDTO> {
        val page = viajesService.getAllViajesAdmin(page, size)
        return PaginadoDTO(
            page.content,
            page.totalElements,
            page.totalPages,
            page.size
        )
    }
}