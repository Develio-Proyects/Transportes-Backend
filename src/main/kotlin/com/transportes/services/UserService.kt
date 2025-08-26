package com.transportes.services

import com.transportes.dto.UserDTO
import com.transportes.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService {
    @Autowired lateinit var userRepository: UserRepository

    fun getUsers(): List<UserDTO> {
        val users = userRepository.findAll()
        return users.map { user ->
            UserDTO(user.name, user.email, user.role.frontName)
        }
    }
}