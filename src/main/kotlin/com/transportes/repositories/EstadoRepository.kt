package com.transportes.repositories

import com.transportes.domain.viajes.Estado
import org.springframework.data.repository.CrudRepository

interface EstadoRepository : CrudRepository<Estado, Long>