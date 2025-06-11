package com.transportes.dto

data class PaginadoDTO<T>(
    val content: List<T>,
    val totalElements: Long,
    val totalPages: Int,
    val pageSize: Int
)
