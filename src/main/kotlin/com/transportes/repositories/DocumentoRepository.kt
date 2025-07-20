package com.transportes.repositories

import com.transportes.domain.documentos.Documento
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface DocumentoRepository : JpaRepository<Documento, String> {
    @Query("SELECT d FROM Documento d WHERE d.unipersonal.id = :userId OR d.empleado.id = :userId")
    fun findByUserId(userId: String): List<Documento>
}