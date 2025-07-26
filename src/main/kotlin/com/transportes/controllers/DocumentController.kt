package com.transportes.controllers

import com.transportes.dto.DocumentDTO
import com.transportes.services.DocumentService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
}