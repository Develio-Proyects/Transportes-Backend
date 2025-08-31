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
        validateNewUser(newUserDTO)

        val user: User = when (newUserDTO.role) {
            Role.SOLO_CARRIER -> {
                SoloCarrier(newUserDTO.email, passwordEncoder.encode(newUserDTO.password), newUserDTO.name, newUserDTO.lastname, newUserDTO.documentNumber)
            }
            Role.MULTI_CARRIER -> {
                MultiCarrier(newUserDTO.email, passwordEncoder.encode(newUserDTO.password), newUserDTO.name, newUserDTO.documentNumber)
            }
            Role.ADMIN -> {
                Administrator(newUserDTO.email, passwordEncoder.encode(newUserDTO.password))
            }
        }
        userRepository.save(user)
    }

    fun validateNewUser(newUserDTO: NewUserDTO) {
        if (userRepository.findByEmail(newUserDTO.email) != null) throw BadRequestException("El email ya está en uso")
    }
}