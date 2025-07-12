package com.transportes.repositories

import com.transportes.domain.Vehiculo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface VehiculoRepository : JpaRepository<Vehiculo, String> {
    @Query("SELECT v FROM Vehiculo v WHERE v.transporte.id = :userId")
    fun findByUserId(userId: String): List<Vehiculo>
}