package com.transportes.dto.user

import com.transportes.dto.DocumentDTO
import com.transportes.dto.trip.UserCompletedTripDTO
import com.transportes.dto.truck.TruckDTO

data class InfoPostulantDTO(
    // Personal info
    val name: String,
    val rol : String,
    val email : String,
    // Documents
    val documents : List<DocumentDTO>,
    // Trucks
    val trucks : List<TruckDTO>,
    // Completed trips
    val completedTrips : List<UserCompletedTripDTO>
)