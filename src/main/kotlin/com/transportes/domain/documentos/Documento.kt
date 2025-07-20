package com.transportes.domain.documentos

import com.transportes.domain.usuarios.Empleado
import com.transportes.domain.usuarios.Unipersonal
import jakarta.persistence.*

@Entity @Table(name = "documentos")
class Documento(
    @ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "id_unipersonal", nullable = true)
    val unipersonal: Unipersonal?,
    @ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "id_empleado", nullable = true)
    val empleado: Empleado?,
    @Column(nullable = true)
    val nombre: String,
    @Column(nullable = false)
    val linkArchivo: String
) {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    lateinit var id: String

    fun getIdUser(): String {
        return if (unipersonal != null) unipersonal!!.id
        else empleado!!.id
    }
}