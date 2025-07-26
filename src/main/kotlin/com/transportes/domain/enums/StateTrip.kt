package com.transportes.domain.enums

enum class StateTrip(val frontName: String) {
    OPEN("En subasta"),
    ASSIGNED("Asignado"),
    CANCELED("Cancelado"),
    IN_PROGRESS("En progreso"),
    FINALIZED("Finalizado")
}