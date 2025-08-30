package com.transportes.repositories

import com.transportes.domain.trips.Offer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface OfferRepository : JpaRepository<Offer, String> {
    @Query("SELECT COUNT(p) FROM Offer p WHERE p.trip.id = :tripId")
    fun getOffersCountByTripId(tripId: String): Long

    @Query("SELECT o FROM Offer o" +
            "        WHERE o.trip.id = :tripId" +
            "          AND o.offeredPrice = (" +
            "              SELECT min(o2.offeredPrice)" +
            "              FROM Offer o2" +
            "              WHERE o2.trip.id = :tripId AND o2.transport.id = o.transport.id" +
            "          )" +
            "        ORDER BY o.offeredPrice ASC")
    fun findAllAscendingByTripId(tripId: String): List<Offer>

    @Query("SELECT p FROM Offer p WHERE p.trip.id = :tripId ORDER BY p.offeredPrice ASC")
    fun findOfferByMinorOffer(tripId: String): List<Offer>
}