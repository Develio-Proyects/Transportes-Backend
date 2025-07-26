package com.transportes.repositories

import com.transportes.domain.enums.StateTrip
import com.transportes.domain.trips.Trip
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface TripRepository : JpaRepository<Trip, String> {
    @Query("SELECT v FROM Trip v WHERE v.state = :stateTrip")
    fun getTripsByState(stateTrip: StateTrip, pageable: Pageable): Page<Trip>

    @Query("SELECT v FROM Trip v WHERE v.multiCarrier.email = :email")
    fun getTripsByMultiCarrierEmail(email: String, pageable: Pageable): Page<Trip>

    @Query("SELECT v FROM Trip v WHERE v.chosenOffer.transport.email = :email")
    fun getTripsByAssignedUserEmail(email: String): List<Trip>

    @Query("SELECT v FROM Trip v JOIN Offer p ON p.trip.id = v.id WHERE p.transport.email = :email AND v.state = StateTrip.OPEN")
    fun getTripsByAppliedUserEmail(email: String): List<Trip>

    @Query("SELECT v FROM Trip v WHERE v.id IN :idList")
    fun findAllByIdList(idList: List<String>, pageable: Pageable): Page<Trip>
}