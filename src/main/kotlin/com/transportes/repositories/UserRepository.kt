package com.transportes.repositories

import com.transportes.domain.usuarios.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CrudRepository<User, String> {
    fun findByEmail(email: String): User?
}