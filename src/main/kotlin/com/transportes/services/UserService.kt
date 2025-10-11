package com.transportes.services

import com.transportes.domain.documents.Document
import com.transportes.domain.enums.Role
import com.transportes.domain.users.Administrator
import com.transportes.domain.users.MultiCarrier
import com.transportes.domain.users.SoloCarrier
import com.transportes.domain.users.User
import com.transportes.dto.user.InfoPostulantDTO
import com.transportes.dto.user.NewUserDTO
import com.transportes.dto.user.UserDTO
import com.transportes.exceptions.BadRequestException
import com.transportes.exceptions.NotFoundException
import com.transportes.repositories.DocumentRepository
import com.transportes.repositories.EmployeeRepository
import com.transportes.repositories.TripRepository
import com.transportes.repositories.TruckRepository
import com.transportes.repositories.UserRepository
import com.transportes.utils.Serializer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService {

    @Autowired private lateinit var tripRepository: TripRepository
    @Autowired lateinit var userRepository: UserRepository
    @Autowired lateinit var documentRepository: DocumentRepository
    @Autowired lateinit var truckRepository: TruckRepository
    @Autowired lateinit var passwordEncoder: PasswordEncoder
    @Autowired lateinit var employeeRepository: EmployeeRepository

    fun getUsers(): List<UserDTO> {
        val users = userRepository.findAll()
        return users.map { user ->
            UserDTO(user.name, user.email, user.role.frontName)
        }
    }

    fun createUser(newUserDTO: NewUserDTO) {
        val normalizedEmail = newUserDTO.email.trim().lowercase()
        val normalizedUserDTO = newUserDTO.copy(email = normalizedEmail)

        validateNewUser(normalizedUserDTO)

        val user: User = when (normalizedUserDTO.role) {
            Role.SOLO_CARRIER -> {
                if (normalizedUserDTO.name == null) throw BadRequestException("El campo 'name' es obligatorio para el rol 'SOLO_CARRIER'")
                if (normalizedUserDTO.lastname == null) throw BadRequestException("El campo 'lastname' es obligatorio para el rol 'SOLO_CARRIER'")
                if (normalizedUserDTO.documentNumber == null) throw BadRequestException("El campo 'documentNumber' es obligatorio para el rol 'SOLO_CARRIER'")
                SoloCarrier(normalizedUserDTO.email, passwordEncoder.encode(normalizedUserDTO.password), normalizedUserDTO.name, normalizedUserDTO.lastname, normalizedUserDTO.documentNumber)
            }
            Role.MULTI_CARRIER -> {
                if (normalizedUserDTO.name == null) throw BadRequestException("El campo 'name' es obligatorio para el rol 'MULTI_CARRIER'")
                if (normalizedUserDTO.documentNumber == null) throw BadRequestException("El campo 'documentNumber' es obligatorio para el rol 'MULTI_CARRIER'")
                MultiCarrier(normalizedUserDTO.email, passwordEncoder.encode(normalizedUserDTO.password), normalizedUserDTO.name, normalizedUserDTO.documentNumber)
            }
            Role.ADMIN -> {
                Administrator(normalizedUserDTO.email, passwordEncoder.encode(normalizedUserDTO.password))
            }
        }
        userRepository.save(user)
    }

    fun validateNewUser(newUserDTO: NewUserDTO) {
        val normalizedEmail = newUserDTO.email.trim().lowercase()
        if (userRepository.findByEmail(normalizedEmail) != null) throw BadRequestException("El email ya está en uso")
    }

    fun getInfoPostulant(userId: String): InfoPostulantDTO {
        val user = userRepository.findById(userId).orElseThrow { NotFoundException("Usuario no encontrado") }

        var documents: ArrayList<Document> = arrayListOf()
        if (user.role == Role.MULTI_CARRIER) {
            val employees = employeeRepository.findByMultiCarrierId(user.id)
            for (employee in employees) {
                val document = documentRepository.findByUserId(employee.id)
                documents.addAll(document)
            }
        } else documents.addAll(documentRepository.findByUserId(userId))

        val trucks = truckRepository.findByUserId(userId)
        val completedTrips = tripRepository.findCompletedTripsByUserId(userId)
        return Serializer.buildInfoPostulantDTO(user, documents, trucks, completedTrips)
    }
}