package com.transportes.domain.documentos

import com.transportes.domain.usuarios.Fletero
import jakarta.persistence.*

@Entity
@Table(name = "documentos_fleteros")
class DocumentoFletero(
    nombre: String,
    descripcion: String,
    linkArchivo: String,
    @ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "id_fletero", nullable = false)
    val fletero: Fletero
) : Documento(nombre, descripcion, linkArchivo)