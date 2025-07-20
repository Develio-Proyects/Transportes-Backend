package com.transportes.domain.usuarios

import jakarta.persistence.*

@Entity
@Table(name = "empleados")
class Empleado(
    @Column(nullable = false)
    val nombre: String,
    @Column(nullable = false)
    val apellido: String,
    @ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "id_flota", nullable = false)
    val flota: Flota
) {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    lateinit var id: String
}