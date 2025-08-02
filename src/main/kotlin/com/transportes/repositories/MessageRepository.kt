package com.transportes.repositories

import com.transportes.domain.Message
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface MessageRepository : JpaRepository<Message, String> {
    @Query("SELECT m FROM Message m WHERE m.tripId = :tripId ORDER BY m.timestamp DESC")
    fun findByTripId(tripId: String): List<Message>
}