package com.transportes.domain.viajes

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable //no va a tener tabla propia, se incrusta dentro de otra entidad
data class Dimensiones(
    @Column(name = "ancho", nullable = false) //mapea cada campo como una columna
    val ancho: Double,

    @Column(name = "alto", nullable = false)
    val alto: Double,

    @Column(name = "largo", nullable = false)
    val largo: Double
){
    //string legible
    override fun toString(): String {
        return "${ancho}m x ${alto}m x ${largo}m"
    }

    //validar dimensiones
    init {
        require(ancho > 0) { "El ancho debe ser mayor a 0" }
        require(alto > 0) { "El alto debe ser mayor a 0" }
        require(largo > 0) { "El largo debe ser mayor a 0" }
    }
}