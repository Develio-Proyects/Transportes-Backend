package com.transportes.domain.trips

import com.fasterxml.jackson.annotation.JsonManagedReference
import com.transportes.domain.enums.StateTrip
import com.transportes.domain.enums.CargoType
import com.transportes.domain.users.MultiCarrier
import com.transportes.dto.trip.TripDTO
import com.transportes.dto.trip.UpdateTripDTO
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity @Table(name = "trips")
class Trip(
    @ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "id_multicarrier", nullable = false)
    val multiCarrier: MultiCarrier,
    @OneToOne(fetch = FetchType.EAGER) @JoinColumn(name = "id_chosen_post", nullable = true)
    @JsonManagedReference // Evita referencia circular
    var chosenOffer: Offer? = null,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var state: StateTrip,
    @Column(nullable = false)
    var origin: String,
    @Column(nullable = false)
    var destination: String,
    @Column(nullable = false)
    var departureDate: LocalDateTime,
    @Column(nullable = false)
    val postedDate: LocalDateTime,
    @Column(nullable = false)
    var basePrice: Double,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var cargoType: CargoType,
    @Column(nullable = false)
    var weight: Double,
    @Embedded
    var dimensions: Dimensions,
    @Column(nullable = false)
    var observations: String? = null
) {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    lateinit var id: String

    fun updateTrip(updateTripDTO: UpdateTripDTO): Trip {
        this.origin = updateTripDTO.origin
        this.destination = updateTripDTO.destination
        this.departureDate = updateTripDTO.departureDate
        this.basePrice = updateTripDTO.basePrice
        this.cargoType = updateTripDTO.cargoType
        this.weight = updateTripDTO.weight
        this.dimensions = updateTripDTO.dimensions
        this.observations = updateTripDTO.observations
        return this
    }
}