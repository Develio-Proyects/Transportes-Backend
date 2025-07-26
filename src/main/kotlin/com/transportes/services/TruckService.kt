package com.transportes.services

import com.transportes.domain.users.User
import com.transportes.dto.truck.TruckDTO
import com.transportes.exceptions.NotFoundException
import com.transportes.repositories.TruckRepository
import com.transportes.utils.Serializer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

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
}