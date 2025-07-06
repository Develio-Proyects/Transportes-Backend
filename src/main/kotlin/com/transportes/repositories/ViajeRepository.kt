package com.transportes.repositories

import com.transportes.domain.viajes.Viaje
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ViajeRepository : JpaRepository<Viaje, String> {
    @Query("SELECT v FROM Viaje v WHERE v.estado.nombre = EstadosViaje.SUBASTA")
    fun getViajesDisponibles(pageable: Pageable): Page<Viaje>

    @Query("SELECT v FROM Viaje v WHERE v.flota.email = :email")
    fun getViajesByFlotaEmail(email: String, pageable: Pageable): Page<Viaje>
}