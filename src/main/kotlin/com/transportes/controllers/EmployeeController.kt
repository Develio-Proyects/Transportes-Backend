package com.transportes.controllers

import com.transportes.dto.EmployeeDTO
import com.transportes.services.EmployeeService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
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
}