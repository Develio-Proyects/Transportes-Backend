package com.transportes.repositories

import com.transportes.domain.users.Employee
import org.springframework.data.jpa.repository.JpaRepository

interface EmployeeRepository : JpaRepository<Employee, String>