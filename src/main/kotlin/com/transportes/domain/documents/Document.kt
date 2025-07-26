package com.transportes.domain.documents

import com.transportes.domain.usuarios.Employee
import com.transportes.domain.usuarios.SoloCarrier
import jakarta.persistence.*

@Entity @Table(name = "document")
class Document(
    @ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "id_solocarrier", nullable = true)
    val soloCarrier: SoloCarrier?,
    @ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "id_employee", nullable = true)
    val employee: Employee?,
    @Column(nullable = true)
    val name: String,
    @Column(nullable = false)
    val linkImage: String
) {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    lateinit var id: String

    fun getIdUser(): String {
        return if (soloCarrier != null) soloCarrier!!.id
        else employee!!.id
    }
}