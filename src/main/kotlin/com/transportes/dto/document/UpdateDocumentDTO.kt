package com.transportes.dto.document

import org.springframework.web.multipart.MultipartFile

data class UpdateDocumentDTO(
    val name: String,
    val image: MultipartFile?
)