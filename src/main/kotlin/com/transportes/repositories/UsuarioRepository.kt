package com.transportes.repositories

import com.transportes.domain.usuarios.Usuario
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UsuarioRepository : CrudRepository<Usuario, String> {
    fun findByEmail(email: String): Usuario?
}