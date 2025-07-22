package com.transportes.repositories

import com.transportes.domain.usuarios.Flota
import org.springframework.data.jpa.repository.JpaRepository

interface FlotaRepository : JpaRepository<Flota, String>