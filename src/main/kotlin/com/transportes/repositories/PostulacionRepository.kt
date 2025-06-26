package com.transportes.repositories

import com.transportes.domain.viajes.Postulacion
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface PostulacionRepository : CrudRepository<Postulacion, String> {
    @Query("SELECT COUNT(p) FROM Postulacion p WHERE p.viaje.id = :viajeId")
    fun getCantidadPostulacionesByViajeId(viajeId: String): Long

    @Query("SELECT p FROM Postulacion p WHERE p.viaje.id = :viajeId ORDER BY p.precioOfrecido ASC")
    fun findAllAscendingByViajeId(viajeId: String): List<Postulacion>
}