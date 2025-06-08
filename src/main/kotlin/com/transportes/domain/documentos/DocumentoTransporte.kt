package com.transportes.domain.documentos

import com.transportes.domain.usuarios.Transporte
import jakarta.persistence.*

@Entity @Table(name = "documentos_transportes")
class DocumentoTransporte(
    nombre: String,
    descripcion: String,
    linkArchivo: String,
    @ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "id_transporte", nullable = false)
    val transporte: Transporte
) : Documento(nombre, descripcion, linkArchivo)