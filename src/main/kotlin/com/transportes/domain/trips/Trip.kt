package com.transportes.domain.trips

import com.fasterxml.jackson.annotation.JsonManagedReference
import com.transportes.domain.enums.StateTrip
import com.transportes.domain.enums.CargoType
import com.transportes.domain.users.MultiCarrier
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
    val state: StateTrip,
    @Column(nullable = false)
    val origin: String,
    @Column(nullable = false)
    val destination: String,
    @Column(nullable = false)
    val departureDate: LocalDateTime,
    @Column(nullable = false)
    val postedDate: LocalDateTime,
    @Column(nullable = false)
    val basePrice: Double,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val cargoType: CargoType,
    @Column(nullable = false)
    val weight: Double,
    @Embedded
    val dimensions: Dimensions,
    @Column(nullable = false)
    val observations: String? = null
) {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    lateinit var id: String
}