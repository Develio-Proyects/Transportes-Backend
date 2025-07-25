package com.transportes.domain.users

import com.transportes.domain.enums.Role
import jakarta.persistence.*

@Entity @Table(name = "administrators")
class Administrator(
    email: String,
    password: String
) : User(email, password, "Administrador", Role.ADMIN)