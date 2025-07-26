package com.transportes.domain.usuarios

import com.transportes.domain.enums.Role
import jakarta.persistence.*

@Entity @Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
abstract class User(
    @Column(nullable = false, unique = true)
    var email: String,
    @Column(nullable = false)
    var password: String,
    @Column(nullable = false)
    var name: String,
    @Column(nullable = false)
    val role: Role
) {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    lateinit var id: String
}