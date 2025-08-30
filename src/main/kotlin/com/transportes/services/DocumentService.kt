package com.transportes.services

import com.transportes.domain.documents.Document
import com.transportes.repositories.DocumentRepository
import com.transportes.utils.Serializer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import com.transportes.domain.users.SoloCarrier
import com.transportes.dto.DocumentDTO
import com.transportes.dto.document.NewDocumentDTO
import com.transportes.exceptions.BadRequestException
import com.transportes.repositories.EmployeeRepository
import com.transportes.repositories.UserRepository
import com.transportes.exceptions.NotFoundException
import com.transportes.repositories.MultiCarrierRepository
import jakarta.transaction.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class DocumentService {
    @Autowired lateinit var documentRepository: DocumentRepository
    @Autowired lateinit var userRepository: UserRepository
    @Autowired lateinit var multicarrierRepository: MultiCarrierRepository
    @Autowired lateinit var employeeRepository: EmployeeRepository
    @Autowired lateinit var imageService: ImageService
    @Autowired lateinit var userDetailsService: MyUserDetailsService

    fun getUserDocuments(idUser: String): List<DocumentDTO> {
        val documentsList = documentRepository.findByUserId(idUser)
        return documentsList.map { document ->
            Serializer.buildDocumentDTO(document)
        }
    }

    @Transactional
    fun createDocument(dto: NewDocumentDTO): DocumentDTO {
        val currentUser = userDetailsService.getCurrentUser() ?: throw BadRequestException("Usuario no autenticado")

        val document = Document(
            soloCarrier = null,
            employee = null,
            name = dto.name,
            linkImage = null
        )

        assignUserInDocument(document, dto.idUser, currentUser.id)

        documentRepository.save(document)

        saveImage(document, dto.image)

        val newDocument = documentRepository.save(document)
        return Serializer.buildDocumentDTO(newDocument)
    }

    fun assignUserInDocument(document: Document, idUser: String, currentUserId: String) {
        if ( employeeRepository.existsById(idUser) ) {

            val employee = employeeRepository.findById(idUser).get()
            if (employee.multiCarrier.id != currentUserId) throw BadRequestException("No puedes agregar documentos a este empleado")
            document.employee = employee

        } else if ( userRepository.existsById(idUser) ) {

            if (multicarrierRepository.existsById(idUser)) throw BadRequestException("No puedes agregar documentos una flota, solo a sus empleados")
            if (currentUserId != idUser) throw BadRequestException("No puedes agregar documentos a este usuario")
            val user = userRepository.findById(idUser).get() as SoloCarrier
            document.soloCarrier = user

        } else throw NotFoundException("Usuario no encontrado")
    }

    fun updateUserDocument(id: String, dto: NewDocumentDTO): DocumentDTO {
        val document = documentRepository.findById(id).orElseThrow { NotFoundException("Documento no encontrado") }
        document.name = dto.name
        saveImage(document, dto.image)

        val updatedDocument = documentRepository.save(document)
        return Serializer.buildDocumentDTO(updatedDocument)
    }

    fun saveImage(document: Document, image: MultipartFile) {
        val extension = image.originalFilename?.substringAfterLast('.', "")
        val filename = if (!extension.isNullOrEmpty()) "${document.id}.$extension" else document.id
        val imagePath = imageService.saveAndGetImagePath(image, filename)
        document.linkImage = imagePath
    }
}