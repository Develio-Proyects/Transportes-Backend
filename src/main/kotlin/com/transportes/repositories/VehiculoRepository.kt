package com.transportes.repositories

import com.transportes.domain.Vehiculo
import org.springframework.data.repository.CrudRepository

interface VehiculoRepository : CrudRepository<Vehiculo, String>