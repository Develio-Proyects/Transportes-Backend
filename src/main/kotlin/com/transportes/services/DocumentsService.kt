package com.transportes.services

import com.transportes.domain.usuarios.Usuario
import com.transportes.dto.DocumentDTO
import com.transportes.exceptions.InvalidCredentialsException
import com.transportes.repositories.DocumentoRepository
import com.transportes.utils.Serializer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DocumentsService {
    @Autowired lateinit var userDetailsService: MyUserDetailsService
    @Autowired lateinit var documentRepository: DocumentoRepository

    fun getUserDocuments(idUser: String): List<DocumentDTO> {
        val user: Usuario? = userDetailsService.getCurrentUser()
        if (user == null) throw InvalidCredentialsException("Usuario no encontrado")
        val listaDocuments = documentRepository.findByUserId(idUser)
        return listaDocuments.map { document ->
            Serializer.buildDocumentDTO(document)
        }
    }
}