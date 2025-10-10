package com.transportes.controllers

import com.transportes.domain.enums.StateTrip
import com.transportes.dto.*
import com.transportes.dto.trip.NewTripDTO
import com.transportes.dto.trip.TripToAdminDTO
import com.transportes.dto.trip.TripDTO
import com.transportes.dto.trip.PostDTO
import com.transportes.dto.trip.TripDetailDTO
import com.transportes.services.TripService
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/trip")
class TripController {
    @Autowired lateinit var tripService: TripService

    @PostMapping()
    @Operation(
        summary = "Create a new trip",
        description = "Creates a new trip using the provided NewTripDTO and returns the created TripDTO."
    )
    fun createTrip(
        @Valid @RequestBody newTrip: NewTripDTO
    ): TripDTO {
        return tripService.createTrip(newTrip)
    }

    @GetMapping("/posted-trips")
    @Operation(
        summary = "Get posted trips",
        description = "Retrieves a paginated list of posted trips. Optionally uses the Authorization token."
    )
    fun getPostedTrips(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestHeader("Authorization", required = false) token: String?
    ): PageableDTO<PostDTO> {
        val trip = tripService.getPostedTrips(token, page, size)
        return PageableDTO(
            trip.content,
            trip.totalElements,
            trip.totalPages,
            trip.size
        )
    }

    @GetMapping("/user-posted-trips")
    @Operation(
        summary = "Get user posted trips",
        description = "Retrieves a paginated list of trips posted by the current user."
    )
    fun getUserPostedTrips(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
    ): PageableDTO<TripDTO> {
        val trip = tripService.getUserPostedTrips(page, size)
        return PageableDTO(
            trip.content,
            trip.totalElements,
            trip.totalPages,
            trip.size
        )
    }

    @GetMapping("/user-trips")
    @Operation(
        summary = "Get user trips",
        description = "Retrieves a paginated list of trips associated with the current user."
    )
    fun getUserTrips(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
    ): PageableDTO<TripDTO> {
        val viaje = tripService.getUserTrips(page, size)
        return PageableDTO(
            viaje.content,
            viaje.totalElements,
            viaje.totalPages,
            viaje.size
        )
    }

    @GetMapping("/{idTrip}")
    @Operation(
        summary = "Get trip detail",
        description = "Retrieves detailed information for a specific trip by its ID."
    )
    fun getTripDetail(
        @PathVariable idTrip: String
    ): TripDetailDTO {
        return tripService.getTripDetail(idTrip)
    }

    @GetMapping("/admin")
    @Operation(
        summary = "Get trips for admin",
        description = "Retrieves a paginated list of trips for administrative purposes."
    )
    fun getTripsToAdmin(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
    ): PageableDTO<TripToAdminDTO> {
        val trip = tripService.getTripsToAdmin(page, size)
        return PageableDTO(
            trip.content,
            trip.totalElements,
            trip.totalPages,
            trip.size
        )
    }

    @PostMapping("/change-state/{idTrip}")
    @Operation(
        summary = "Change trip state",
        description = "Changes the state of a specific trip"
    )
    fun changeState(
        @PathVariable idTrip: String,
        @RequestParam state: StateTrip,
    ): String {
        tripService.changeTripState(idTrip, state)
        return "Estado actualizado correctamente"
    }

    @DeleteMapping("/{idTrip}")
    @Operation(
        summary = "Delete a trip",
        description = "Deletes a trip during deletion."
    )fun deleteTrip(
        @PathVariable idTrip: String,
    ):String{
        tripService.deleteTrip(idTrip)
        return "Publicación Eliminada"
    }
}