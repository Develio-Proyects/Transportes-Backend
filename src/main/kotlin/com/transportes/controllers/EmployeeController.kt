package com.transportes.controllers

import com.transportes.dto.employee.EmployeeDTO
import com.transportes.dto.employee.NewEmployeeDTO
import com.transportes.services.EmployeeService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/employee")
class EmployeeController {
    @Autowired lateinit var employeeService: EmployeeService

    @GetMapping
    @Operation(
        summary = "Get all employees of multi carrier",
        description = "Returns a list of employees."
    )
    fun getUsers(): List<EmployeeDTO> {
        return employeeService.getEmployees()
    }

    @PostMapping
    @Operation(
        summary = "Create a new employee",
        description = "Creates a new employee and returns the created employee details."
    )
    fun createEmployee(
        @RequestBody employee: NewEmployeeDTO
    ): EmployeeDTO {
        return employeeService.createEmployee(employee)
    }

    @PutMapping("/{employeeId}")
    @Operation(
        summary = "Update an existing employee",
        description = "Updates an existing employee and returns the updated employee details."
    )
    fun updateEmployee(
        @PathVariable employeeId: String,
        @RequestBody updatedEmployee: NewEmployeeDTO
    ): EmployeeDTO {
        return employeeService.updateEmployee(employeeId, updatedEmployee)
    }
}