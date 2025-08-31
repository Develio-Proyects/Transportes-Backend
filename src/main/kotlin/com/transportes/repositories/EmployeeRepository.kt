package com.transportes.repositories

import com.transportes.domain.users.Employee
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface EmployeeRepository : JpaRepository<Employee, String> {
    @Query("select e from Employee e where e.multiCarrier.id = :multiCarrierId")
    fun findByMultiCarrierId(multiCarrierId: String): List<Employee>
}