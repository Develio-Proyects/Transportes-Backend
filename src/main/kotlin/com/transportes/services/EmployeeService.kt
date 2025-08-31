package com.transportes.services

import com.transportes.domain.users.Employee
import com.transportes.dto.employee.EmployeeDTO
import com.transportes.dto.employee.NewEmployeeDTO
import com.transportes.exceptions.InvalidCredentialsException
import com.transportes.exceptions.NotFoundException
import com.transportes.repositories.EmployeeRepository
import com.transportes.repositories.MultiCarrierRepository
import com.transportes.utils.Serializer.buildEmployeeDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class EmployeeService {
    @Autowired lateinit var employeeRepository: EmployeeRepository
    @Autowired lateinit var userDetailsService: MyUserDetailsService
    @Autowired lateinit var multiCarrierRepository: MultiCarrierRepository

    fun getEmployees(): List<EmployeeDTO> {
        val currentUser = userDetailsService.getCurrentUser() ?: throw InvalidCredentialsException("Usuario no autenticado")
        val employeeList = employeeRepository.findByMultiCarrierId(currentUser.id)
        return employeeList.map { employee -> buildEmployeeDTO(employee) }
    }

    fun createEmployee(newEmployee: NewEmployeeDTO): EmployeeDTO {
        val currentUser = userDetailsService.getCurrentUser() ?: throw InvalidCredentialsException("Usuario no autenticado")
        val multicarrier = multiCarrierRepository.findById(currentUser.id).get()
        val employee = Employee(
            newEmployee.name,
            newEmployee.lastname,
            multicarrier
        )
        val newEmployee = employeeRepository.save(employee)
        return buildEmployeeDTO(newEmployee)
    }

    fun updateEmployee(employeeId: String, updatedEmployee: NewEmployeeDTO): EmployeeDTO {
        val currentUser = userDetailsService.getCurrentUser() ?: throw InvalidCredentialsException("Usuario no autenticado")
        val employee = employeeRepository.findById(employeeId).orElseThrow { NotFoundException("Empleado no encontrado") }

        if (employee.multiCarrier.id != currentUser.id) throw InvalidCredentialsException("No tienes permiso para actualizar este empleado")

        employee.name = updatedEmployee.name
        employee.lastname = updatedEmployee.lastname

        val savedEmployee = employeeRepository.save(employee)
        return buildEmployeeDTO(savedEmployee)
    }
}