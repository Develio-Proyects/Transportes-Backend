package com.transportes.domain.documentos

import com.transportes.domain.usuarios.Transporte
import jakarta.persistence.*

@Entity @Table(name = "documentos")
class Documento(
    @ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "id_transporte", nullable = false)
    val transporte: Transporte,
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