package com.transportes.domain.documentos

import jakarta.persistence.*

@Entity @Table(name = "documentos")
@Inheritance(strategy = InheritanceType.JOINED)
abstract class Documento(
    @Column(nullable = true)
    val nombre: String,
    @Column(nullable = true)
    val descripcion: String,
    @Column(nullable = false)
    val linkArchivo: String
) {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    lateinit var id: String
}