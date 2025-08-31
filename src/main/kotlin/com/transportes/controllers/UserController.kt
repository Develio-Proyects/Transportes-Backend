package com.transportes.controllers

import com.transportes.dto.NewUserDTO
import com.transportes.dto.UserDTO
import com.transportes.services.UserService
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserController {
    @Autowired lateinit var userService: UserService

    @GetMapping
    @Operation(
        summary = "Get all user",
        description = "Returns a list of users."
    )
    fun getUsers(): List<UserDTO> {
        return userService.getUsers()
    }

    @PostMapping
    @Operation(
        summary = "Create account",
        description = "Creates a new user account."
    )
    fun createAccount(
        @Valid @RequestBody newUserDTO: NewUserDTO
    ): String {
        userService.createUser(newUserDTO)
        return "Usuario creado correctamente"
    }
}