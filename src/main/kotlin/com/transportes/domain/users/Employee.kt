package com.transportes.domain.users

import jakarta.persistence.*

@Entity
@Table(name = "employees")
class Employee(
    @Column(nullable = false)
    var name: String,
    @Column(nullable = false)
    var lastname: String,
    @ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "id_multicarrier", nullable = false)
    val multiCarrier: MultiCarrier
) {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    lateinit var id: String
}