package com.transportes.controllers

import com.transportes.dto.ViajeDisponibleDTO
import com.transportes.dto.PaginadoDTO
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

    @GetMapping("/{id}")
    fun getDetalleViaje(@PathVariable id: String) = viajesService.getDetalleViaje(id)

    @GetMapping("/postulacion/tarifa/{id}")
    fun getTarifaViaje(@PathVariable id: String): ResponseEntity<Map<String, Double>> {
        val tarifa = viajesService.getTarifaViaje(id)
        return ResponseEntity.ok( mapOf("tarifa" to tarifa) )
    }
}