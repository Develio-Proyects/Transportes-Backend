package com.transportes.domain.enums

enum class Role(val frontName: String) {
    ADMIN("ADMINISTRADOR"),
    SOLO_CARRIER("UNIPERSONAL"),
    MULTI_CARRIER("FLOTA"),
}