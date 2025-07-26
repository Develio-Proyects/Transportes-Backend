package com.transportes.repositories

import com.transportes.domain.usuarios.Employee
import org.springframework.data.jpa.repository.JpaRepository

interface EmployeeRepository : JpaRepository<Employee, String>