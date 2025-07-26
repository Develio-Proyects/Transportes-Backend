package com.transportes.domain.users

import com.transportes.domain.enums.Role
import jakarta.persistence.*

@Entity @Table(name = "transports")
@Inheritance(strategy = InheritanceType.JOINED)
abstract class Transport(
    name: String,
    email: String,
    password: String,
    rol: Role
) : User(email, password, name, rol)