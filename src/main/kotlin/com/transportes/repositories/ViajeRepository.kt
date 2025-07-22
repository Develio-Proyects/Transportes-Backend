package com.transportes.repositories

import com.transportes.domain.enums.EstadosViaje
import com.transportes.domain.viajes.Viaje
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ViajeRepository : JpaRepository<Viaje, String> {
    @Query("SELECT v FROM Viaje v WHERE v.estado.nombre = :estadosViaje")
    fun getViajesByEstado(estadosViaje: EstadosViaje, pageable: Pageable): Page<Viaje>

    @Query("SELECT v FROM Viaje v WHERE v.flota.email = :email")
    fun getViajesByFlotaEmail(email: String, pageable: Pageable): Page<Viaje>

    @Query("SELECT v FROM Viaje v WHERE v.postulacionElegida.transporte.email = :email")
    fun getViajesByTransporteElegidoUser(email: String): List<Viaje>

    @Query("SELECT v FROM Viaje v JOIN Postulacion p ON p.viaje.id = v.id WHERE p.transporte.email = :email AND v.estado.nombre = 'SUBASTA'")
    fun getViajesByPostulanteUser(email: String): List<Viaje>

    @Query("SELECT v FROM Viaje v WHERE v.id IN :idList")
    fun findAllByIds(idList: List<String> , pageable: Pageable): Page<Viaje>
}