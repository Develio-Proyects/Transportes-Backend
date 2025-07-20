package com.transportes.repositories

import com.transportes.domain.usuarios.Empleado
import org.springframework.data.jpa.repository.JpaRepository

interface EmpleadoRepository : JpaRepository<Empleado, String>