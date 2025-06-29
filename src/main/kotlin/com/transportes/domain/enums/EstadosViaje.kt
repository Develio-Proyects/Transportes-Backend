package com.transportes.domain.enums

enum class EstadosViaje(val frontName: String) {
    SUBASTA("En subasta"),
    ACORDADO("Acordado"),
    CANCELADO("Cancelado"),
    CURSO("En curso"),
    FINALIZADO("Finalizado")
}