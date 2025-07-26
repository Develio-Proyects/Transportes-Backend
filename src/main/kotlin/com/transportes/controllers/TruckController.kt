package com.transportes.controllers

import com.transportes.dto.vehiculo.TruckDTO
import com.transportes.services.TruckService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/truck")
class TruckController {
    @Autowired lateinit var truckService: TruckService

    @GetMapping
    @Operation(
        summary = "Get all trucks for the user",
        description = "Returns a list of trucks associated with the authenticated user."
    )
    fun getTrucks(): List<TruckDTO> {
        return truckService.getUserTrucks()
    }
}