package com.transportes.repositories

import com.transportes.domain.documentos.Documento
import org.springframework.data.repository.CrudRepository

interface DocumentoRepository : CrudRepository<Documento, String>