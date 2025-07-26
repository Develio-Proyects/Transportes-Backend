package com.transportes.repositories

import com.transportes.domain.usuarios.MultiCarrier
import org.springframework.data.jpa.repository.JpaRepository

interface MultiCarrierRepository : JpaRepository<MultiCarrier, String>