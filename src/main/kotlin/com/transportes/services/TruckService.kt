package com.transportes.services

import com.transportes.domain.Truck
import com.transportes.domain.users.Transport
import com.transportes.domain.users.User
import com.transportes.dto.truck.TruckDTO
import com.transportes.exceptions.NotFoundException
import com.transportes.repositories.TruckRepository
import com.transportes.utils.Serializer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import com.transportes.exceptions.InvalidCredentialsException


@Service
class TruckService {
    @Autowired lateinit var userDetailsService: MyUserDetailsService
    @Autowired lateinit var truckRepository: TruckRepository

    fun getUserTrucks(): List<TruckDTO> {
        val user: User = userDetailsService.getCurrentUser()?: throw NotFoundException("Usuario no encontrado")
        val truckList = truckRepository.findByUserId(user.id)
        return truckList.map { vehiculo ->
            Serializer.buildTruckDTO(vehiculo)
        }
    }

    fun createTruck(truckDTO: TruckDTO): TruckDTO {
        val user = userDetailsService.getCurrentUser() ?: throw NotFoundException("Usuario no encontrado")

        val truck = Truck(
            brand = truckDTO.brand,
            model = truckDTO.model,
            patent = truckDTO.patent,
            transport = user as Transport
        )

        val saved = truckRepository.save(truck)
        return Serializer.buildTruckDTO(saved)
    }

    fun updateTruck(id: String, truckDTO: TruckDTO): TruckDTO {
        val user = userDetailsService.getCurrentUser() ?: throw NotFoundException("Usuario no encontrado")

        val truck = truckRepository.findById(id)
            .orElseThrow { NotFoundException("Vehículo no encontrado") }

        if (truck.transport.id != user.id)
            throw InvalidCredentialsException("No puedes editar este vehículo")

        truck.update(truckDTO)

        truckRepository.save(truck)
        return Serializer.buildTruckDTO(truck)
    }
}