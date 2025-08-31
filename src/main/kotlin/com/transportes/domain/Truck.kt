package com.transportes.domain

import com.transportes.domain.users.Transport
import com.transportes.dto.truck.NewTruckDTO
import jakarta.persistence.*

@Entity @Table(name = "trucks")
class Truck(
    @Column(nullable = false)
    var brand: String,
    @Column(nullable = false)
    var model: String,
    @Column(nullable = false)
    var patent: String,
    @ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "id_transport", nullable = false)
    val transport: Transport
) {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    lateinit var id: String

    fun update(truckDTO: NewTruckDTO) {
        this.brand = truckDTO.brand
        this.model = truckDTO.model
        this.patent = truckDTO.patent
    }
}