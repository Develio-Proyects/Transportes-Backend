package com.transportes.repositories

import com.transportes.domain.documents.Document
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface DocumentRepository : JpaRepository<Document, String> {
    @Query("SELECT d FROM Document d WHERE d.soloCarrier.id = :userId OR d.employee.id = :userId")
    fun findByUserId(userId: String): List<Document>
}