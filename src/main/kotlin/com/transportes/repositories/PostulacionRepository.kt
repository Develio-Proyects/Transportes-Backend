package com.transportes.repositories

import com.transportes.domain.viajes.Postulacion
import org.springframework.data.repository.CrudRepository

interface PostulacionRepository : CrudRepository<Postulacion, String>