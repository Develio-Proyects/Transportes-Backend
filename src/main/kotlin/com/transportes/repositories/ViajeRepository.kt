package com.transportes.repositories

import com.transportes.domain.viajes.Viaje
import org.springframework.data.repository.CrudRepository

interface ViajeRepository : CrudRepository<Viaje, String>