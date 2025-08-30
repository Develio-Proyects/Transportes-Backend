package com.transportes.services

import com.transportes.domain.documents.Document
import com.transportes.repositories.DocumentRepository
import com.transportes.utils.Serializer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import com.transportes.domain.users.SoloCarrier
import com.transportes.dto.DocumentDTO
import com.transportes.dto.document.NewDocumentDTO
import com.transportes.repositories.EmployeeRepository
import com.transportes.repositories.UserRepository
import com.transportes.exceptions.NotFoundException

@Service
class DocumentService {
    @Autowired lateinit var documentRepository: DocumentRepository
    @Autowired lateinit var userRepository: UserRepository
    @Autowired lateinit var employeeRepository: EmployeeRepository

    fun getUserDocuments(idUser: String): List<DocumentDTO> {
        val documentsList = documentRepository.findByUserId(idUser)
        return documentsList.map { document ->
            Serializer.buildDocumentDTO(document)
        }
    }

    fun createDocument(dto: NewDocumentDTO): DocumentDTO {
        val document = Document(
            soloCarrier = null,
            employee = null,
            name = dto.name,
            linkImage = dto.fileLink
        )

        if (employeeRepository.existsById(dto.idUser)) {
            document.employee = employeeRepository.findById(dto.idUser).get()
        } else if (userRepository.existsById(dto.idUser)) {
            document.soloCarrier = userRepository.findById(dto.idUser).get() as SoloCarrier?
        } else throw NotFoundException("Usuario no encontrado")

        val newDocument = documentRepository.save(document)
        return Serializer.buildDocumentDTO(newDocument)
    }

    fun updateUserDocument(id: String, dto: NewDocumentDTO): DocumentDTO {
        val document = documentRepository.findById(id).orElseThrow { NotFoundException("Documento no encontrado") }
        document.name = dto.name
        document.linkImage = dto.fileLink

        val updatedDocument = documentRepository.save(document)
        return Serializer.buildDocumentDTO(updatedDocument)
    }
}