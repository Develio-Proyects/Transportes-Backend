package com.transportes.dto

data class PageableDTO<T>(
    val content: List<T>,
    val totalElements: Long,
    val totalPages: Int,
    val pageSize: Int
)
