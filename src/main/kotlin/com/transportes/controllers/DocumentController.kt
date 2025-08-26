package com.transportes.controllers

import com.transportes.dto.document.DocumentDTO
import com.transportes.services.DocumentService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/document")
class DocumentController {
    @Autowired lateinit var documentService: DocumentService

    @GetMapping("/{idUser}")
    @Operation(
        summary = "Get documents for a user",
        description = "Returns a list of documents associated with the specified user ID"
    )
    fun getUserDocuments(@PathVariable idUser: String): List<DocumentDTO> {
        return documentService.getUserDocuments(idUser)
    }

    @PostMapping
    fun createDocument(@RequestBody dto: DocumentDTO): ResponseEntity<DocumentDTO> {
        val document = documentService.createDocument(dto)
        val response = DocumentDTO(
            idUser = document.getIdUser(),
            name = document.name,
            fileLink = document.linkImage
        )
        return ResponseEntity.ok(response)
    }
}