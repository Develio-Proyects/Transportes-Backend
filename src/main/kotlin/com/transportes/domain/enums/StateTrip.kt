package com.transportes.domain.enums

enum class StateTrip(val frontName: String) {
    OPEN("En subasta"),
    ASSIGNED("Asignado"),
    PROGRESS("En progreso"),
    CANCELED("Cancelado"),
    FINALIZED("Finalizado")
}