package com.transportes.repositories

import com.transportes.domain.trips.Offer
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface OfferRepository : CrudRepository<Offer, String> {
    @Query("SELECT COUNT(p) FROM Offer p WHERE p.trip.id = :viajeId")
    fun getOffersCountByTripId(viajeId: String): Long

    @Query("SELECT p FROM Offer p WHERE p.trip.id = :viajeId ORDER BY p.offeredPrice ASC")
    fun findAllAscendingByTripId(viajeId: String): List<Offer>
}