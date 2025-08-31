package com.transportes.controllers

import com.transportes.dto.truck.NewTruckDTO
import com.transportes.dto.truck.TruckDTO
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

    @PostMapping
    @Operation(
        summary = "Create a new truck for the authenticated transport user"
    )
    fun createTruck(@RequestBody truckDTO: NewTruckDTO): TruckDTO {
        return truckService.createTruck(truckDTO)
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Update a truck", description = "Updates a truck owned by the authenticated user"
    )
    fun updateTruck(
        @PathVariable id: String,
        @RequestBody newTruckDTO: NewTruckDTO
    ): TruckDTO {
        return truckService.updateTruck(id, newTruckDTO)
    }
}