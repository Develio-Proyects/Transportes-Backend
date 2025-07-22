package com.transportes.controllers

import com.transportes.dto.*
import com.transportes.dto.viajes.NuevoViajeDTO
import com.transportes.dto.viajes.ViajeAdminDTO
import com.transportes.dto.viajes.ViajeDTO
import com.transportes.dto.viajes.ViajeDisponibleDTO
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
    ): PaginadoDTO<ViajeDTO> {
        val viaje = viajesService.getMisPublicaciones(page, size)
        return PaginadoDTO(
            viaje.content,
            viaje.totalElements,
            viaje.totalPages,
            viaje.size
        )
    }

    @GetMapping("/acordados")
    fun getMisViajesAcordados(
        @RequestParam page: Int,
        @RequestParam size: Int
    ): PaginadoDTO<ViajeDTO> {
        val viaje = viajesService.getViajesAcordados(page, size)
        return PaginadoDTO(
            viaje.content,
            viaje.totalElements,
            viaje.totalPages,
            viaje.size
        )
    }

    @GetMapping("/postulados")
    fun getMisViajesPostulados(
        @RequestParam page: Int,
        @RequestParam size: Int
    ): PaginadoDTO<ViajeDTO> {
        val page = viajesService.getViajesPostulados(page, size)
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
        val viaje = viajesService.getAllViajesAdmin(page, size)
        return PaginadoDTO(
            viaje.content,
            viaje.totalElements,
            viaje.totalPages,
            viaje.size
        )
    }

    @PostMapping()
    fun crearViaje(@RequestBody viaje: NuevoViajeDTO) = viajesService.crearViaje(viaje)
}