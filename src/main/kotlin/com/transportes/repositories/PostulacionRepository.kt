package com.transportes.repositories

import com.transportes.domain.viajes.Postulacion
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface PostulacionRepository : CrudRepository<Postulacion, String> {
    @Query("SELECT COUNT(p) FROM Postulacion p WHERE p.viaje.id = :viajeId")
    fun getCantidadPostulacionesByViajeId(viajeId: String): Long

    //devuelve las postulaciones de x viaje
    fun findAllByViajeId(viajeId: String): List<Postulacion>
}