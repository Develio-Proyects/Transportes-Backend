package com.transportes.domain

import com.transportes.domain.usuarios.Transporte
import jakarta.persistence.*

@Entity @Table(name = "vehiculos")
class Vehiculo(
    @Column(nullable = false)
    val marca: String,
    @Column(nullable = false)
    val modelo: String,
    @Column(nullable = false)
    val patente: String,
    @Column(nullable = false)
    val urlTituloCamion: String,
    @Column(nullable = false)
    val urlTituloSemi: String,
    @ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "id_transporte", nullable = false)
    val transporte: Transporte
) {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    lateinit var id: String
}