package com.transportes.controllers

import com.transportes.dto.DocumentDTO
import com.transportes.dto.document.NewDocumentDTO
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

    @PostMapping(consumes = ["multipart/form-data"])
    fun createDocument(
        @ModelAttribute dto: NewDocumentDTO
    ): ResponseEntity<DocumentDTO> {
        val document = documentService.createDocument(dto)
        return ResponseEntity.ok(document)
    }

    @PutMapping("/{idDocument}", consumes = ["multipart/form-data"])
    fun updateDocument(
        @PathVariable idDocument: String,
        @ModelAttribute updatedDocumentDTO: NewDocumentDTO
    ): ResponseEntity<DocumentDTO> {
        // TODO
        // Verificar que el documento a actualizar pertenezca al solo carrier logueado o a un empleado del multi carrier logueado
        // Eliminar la imagen del documento y luego guardar la nueva. Si no va a haber dos archivos con un mismo idDocumento
        // Si la carpeta image no existe, crearla
        val document = documentService.updateUserDocument(idDocument, updatedDocumentDTO)
        return ResponseEntity.ok(document)
    }
}