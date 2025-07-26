package com.transportes.services

import com.transportes.dto.DocumentDTO
import com.transportes.repositories.DocumentRepository
import com.transportes.utils.Serializer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DocumentService {
    @Autowired lateinit var documentRepository: DocumentRepository

    fun getUserDocuments(idUser: String): List<DocumentDTO> {
        val documentsList = documentRepository.findByUserId(idUser)
        return documentsList.map { document ->
            Serializer.buildDocumentDTO(document)
        }
    }
}