package com.transportes.services

import com.transportes.domain.enums.Role
import com.transportes.domain.users.Administrator
import com.transportes.domain.users.MultiCarrier
import com.transportes.domain.users.SoloCarrier
import com.transportes.domain.users.User
import com.transportes.dto.user.NewUserDTO
import com.transportes.dto.user.UserDTO
import com.transportes.exceptions.BadRequestException
import com.transportes.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService {

    @Autowired lateinit var userRepository: UserRepository
    @Autowired lateinit var passwordEncoder: PasswordEncoder

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
}