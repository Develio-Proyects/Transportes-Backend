package com.transportes.services

import com.transportes.domain.documents.Document
import com.transportes.dto.document.DocumentDTO
import com.transportes.repositories.DocumentRepository
import com.transportes.utils.Serializer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import com.transportes.exceptions.NotFoundException
import com.transportes.exceptions.BadRequestException

@Service
class DocumentService {
    @Autowired lateinit var documentRepository: DocumentRepository

    fun getUserDocuments(idUser: String): List<DocumentDTO> {
        val documentsList = documentRepository.findByUserId(idUser)
        return documentsList.map { document ->
            Serializer.buildDocumentDTO(document)
        }
    }

    fun updateUserDocument(dto: DocumentDTO): Document {
        val document = documentRepository.findById(dto.id ?: "")
            .orElseThrow{ NotFoundException("Documento no encontrado") }

        if (document.getIdUser() != dto.idUser) {
            throw BadRequestException("El documento no pertenece al usuario indicado")
        }

        document.name = dto.name
        document.linkImage = dto.fileLink
        return documentRepository.save(document)
    }
}