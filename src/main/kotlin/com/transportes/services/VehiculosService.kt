package com.transportes.services

import com.transportes.domain.usuarios.Usuario
import com.transportes.dto.vehiculo.VehiculoDTO
import com.transportes.exceptions.InvalidCredentialsException
import com.transportes.repositories.VehiculoRepository
import com.transportes.utils.Serializer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class VehiculosService {
    @Autowired lateinit var userDetailsService: MyUserDetailsService
    @Autowired lateinit var carsRepository: VehiculoRepository

    fun getUserCars(): List<VehiculoDTO> {
        val user: Usuario? = userDetailsService.getCurrentUser()
        if (user == null) { throw InvalidCredentialsException("Usuario no encontrado") }
        val listaVehiculos = carsRepository.findByUserId(user.id)
        return listaVehiculos.map { vehiculo ->
            Serializer.buildVehiculoDTO(vehiculo)
        }
    }
}