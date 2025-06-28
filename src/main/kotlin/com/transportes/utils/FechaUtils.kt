package com.transportes.utils

import java.time.Duration
import java.time.LocalDateTime

object FechaUtils {
    fun tiempoTranscurrido(fecha: LocalDateTime): String {
        val ahora = LocalDateTime.now()
        val duracion = Duration.between(fecha, ahora)

        val diasTranscurridos = duracion.toDays()
        return if (diasTranscurridos >= 1) {
            val dias = diasTranscurridos
            "Publicado hace $dias ${if (dias == 1L) "día" else "días"}"
        } else "Recientemente agregada"
    }
}