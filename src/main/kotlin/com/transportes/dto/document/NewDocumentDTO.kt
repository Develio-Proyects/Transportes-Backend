package com.transportes.dto.document

import org.springframework.web.multipart.MultipartFile

data class NewDocumentDTO(
    val idUser: String,
    val name: String,
    val image: MultipartFile?
)