package com.transportes.dto

data class DocumentDTO(
    val id: String,
    val idUser: String,
    val name: String,
    val lastname: String,
    val documentName: String,
    val fileLink: String
)