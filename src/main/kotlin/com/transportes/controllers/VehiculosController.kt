package com.transportes.controllers

import com.transportes.services.VehiculosService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/vehiculos")
class VehiculosController {
    @Autowired
    lateinit var vehiculosService: VehiculosService

    @GetMapping
    fun getViajesDisponibles() = vehiculosService.getUserCars()
}