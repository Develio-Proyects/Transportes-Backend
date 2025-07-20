package com.transportes.controllers

import com.transportes.services.DocumentsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/documentos")
class DocumentsController {
    @Autowired lateinit var documentsService: DocumentsService

    @GetMapping("/{idUser}")
    fun getUserDocuments(
        @PathVariable idUser: String
    ) = documentsService.getUserDocuments(idUser)
}