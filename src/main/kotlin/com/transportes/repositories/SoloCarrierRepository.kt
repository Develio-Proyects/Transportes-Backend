package com.transportes.repositories

import com.transportes.domain.users.SoloCarrier
import org.springframework.data.jpa.repository.JpaRepository

interface SoloCarrierRepository : JpaRepository<SoloCarrier, String>