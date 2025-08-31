package com.transportes.services

import com.transportes.dto.EmployeeDTO
import com.transportes.exceptions.InvalidCredentialsException
import com.transportes.repositories.EmployeeRepository
import com.transportes.utils.Serializer.buildEmployeeDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class EmployeeService {
    @Autowired lateinit var employeeRepository: EmployeeRepository
    @Autowired lateinit var userDetailsService: MyUserDetailsService

    fun getEmployees(): List<EmployeeDTO> {
        val currentUser = userDetailsService.getCurrentUser() ?: throw InvalidCredentialsException("Usuario no autenticado")
        val employeeList = employeeRepository.findByMultiCarrierId(currentUser.id)
        return employeeList.map { employee -> buildEmployeeDTO(employee) }
    }
}