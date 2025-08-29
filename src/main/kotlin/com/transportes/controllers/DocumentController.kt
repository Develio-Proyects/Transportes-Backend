package com.transportes.controllers

import com.transportes.dto.document.DocumentDTO
import com.transportes.services.DocumentService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity

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

    @PutMapping("/{id}")
    fun updateDocument(
        @PathVariable id: String,
        @RequestBody dto: DocumentDTO
    ): ResponseEntity<DocumentDTO> {
        val updated = documentService.updateUserDocument(dto.copy(id = id))
        val response = DocumentDTO(
            id = updated.id,
            idUser = updated.getIdUser(),
            name = updated.name,
            fileLink = updated.linkImage
        )
        return ResponseEntity.ok(response)
    }
}