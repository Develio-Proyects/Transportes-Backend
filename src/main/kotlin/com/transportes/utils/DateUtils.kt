package com.transportes.utils

import java.time.Duration
import java.time.LocalDateTime

object DateUtils {
    fun spentTime(fecha: LocalDateTime): String {
        val now = LocalDateTime.now()
        val spentTime = Duration.between(fecha, now)

        val spentDays = spentTime.toDays()
        return if (spentDays >= 1) {
            val days = spentDays
            "Publicado hace $days ${if (days == 1L) "día" else "días"}"
        } else "Recientemente agregada"
    }
}