package com.transportes.repositories

import com.transportes.domain.Truck
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface TruckRepository : JpaRepository<Truck, String> {
    @Query("SELECT v FROM Truck v WHERE v.transport.id = :userId")
    fun findByUserId(userId: String): List<Truck>
}