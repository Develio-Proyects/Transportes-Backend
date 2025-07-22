package com.transportes.services

import com.transportes.domain.viajes.Viaje
import com.transportes.dto.viajes.NuevoViajeDTO
import com.transportes.dto.viajes.ViajeAdminDTO
import com.transportes.dto.viajes.ViajeDTO
import com.transportes.dto.viajes.ViajeDetalleDTO
import com.transportes.dto.viajes.ViajeDisponibleDTO
import com.transportes.exceptions.BadRequestException
import com.transportes.exceptions.InvalidCredentialsException
import com.transportes.exceptions.NotFoundException
import com.transportes.repositories.FlotaRepository
import com.transportes.repositories.PostulacionRepository
import com.transportes.repositories.ViajeRepository
import com.transportes.utils.Serializer
import com.transportes.utils.Serializer.buildViajeAdminDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ViajesService {
    @Autowired lateinit var viajesRepository: ViajeRepository
    @Autowired lateinit var flotaRepository: FlotaRepository
    @Autowired lateinit var postulacionesRepository: PostulacionRepository
    @Autowired lateinit var userDetailsService: MyUserDetailsService

    fun getViajesDisponibles(token: String?, page: Int, size: Int): Page<ViajeDisponibleDTO> {
        val page: Pageable = Pageable.ofSize(size).withPage(page)
        val user = if (token != null) {
            try { userDetailsService.loadUserByToken(token) }
            catch (e: InvalidCredentialsException) { null }
        } else null

        val listaViajes = viajesRepository.getViajesDisponibles(page)
        return listaViajes.map {
            val cantPostulaciones = postulacionesRepository.getCantidadPostulacionesByViajeId(it.id)
            val miPublicacion = if (user != null) it.flota.id == user.id else false
            Serializer.buildViajeDisponibleDTO(it, cantPostulaciones, miPublicacion)
        }
    }

    fun getMisPublicaciones(page: Int, size: Int): Page<ViajeDTO> {
        val page: Pageable = Pageable.ofSize(size).withPage(page)
        val user = userDetailsService.getCurrentUser() ?: throw BadRequestException("Usuario no autenticado")

        val listaViajes = viajesRepository.getViajesByFlotaEmail(user.email, page)
        return listaViajes.map {
            val cantPostulaciones = postulacionesRepository.getCantidadPostulacionesByViajeId(it.id)
            Serializer.buildViajeDTO(it, cantPostulaciones)
        }
    }

    fun getViajesAcordados(page: Int, size: Int): Page<ViajeDTO> {
        val page: Pageable = Pageable.ofSize(size).withPage(page)
        val user = userDetailsService.getCurrentUser() ?: throw BadRequestException("Usuario no autenticado")

        val listaViajes = viajesRepository.getViajesByTransporteElegidoEmail(user.email, page)
        return listaViajes.map {
            val cantPostulaciones = postulacionesRepository.getCantidadPostulacionesByViajeId(it.id)
            Serializer.buildViajeDTO(it, cantPostulaciones)
        }
    }

    fun getViajesPostulados(page: Int, size: Int): Page<ViajeDTO> {
        val page: Pageable = Pageable.ofSize(size).withPage(page)
        val user = userDetailsService.getCurrentUser() ?: throw BadRequestException("Usuario no autenticado")

        val listaViajes = viajesRepository.getViajesByPostulanteEmail(user.email, page)
        return listaViajes.map {
            val cantPostulaciones = postulacionesRepository.getCantidadPostulacionesByViajeId(it.id)
            Serializer.buildViajeDTO(it, cantPostulaciones)
        }
    }

    fun getDetalleViaje(id: String): ViajeDetalleDTO {
        val viaje: Viaje = viajesRepository.findById(id).orElseThrow{ NotFoundException("Viaje con id $id no fue encontrado") }
        val postulaciones = postulacionesRepository.findAllAscendingByViajeId(viaje.id)
        return Serializer.buildDetalleViajeDTO(viaje, postulaciones)
    }

    fun getTarifaViaje(id: String): Double {
        val postulacion = postulacionesRepository.findById(id).orElseThrow { NotFoundException("Postulacion con id $id no fue encontrada") }
        return postulacion.precioOfrecido * 0.2
    }

    fun getAllViajesAdmin(page: Int, size: Int): Page<ViajeAdminDTO> {
        val page: Pageable = Pageable.ofSize(size).withPage(page)
        val viajes = viajesRepository.findAll(page)
        return viajes.map { viaje -> buildViajeAdminDTO(viaje) }
    }

    fun crearViaje(viaje: NuevoViajeDTO): ViajeDTO {
        val user = userDetailsService.getCurrentUser() ?: throw InvalidCredentialsException("Credenciales inválidas")
        val flota = flotaRepository.findById(user.id).orElseThrow { BadRequestException("Credenciales inválidas") }

        val nuevoViaje: Viaje = Serializer.buildViajeByNuevoViajeDTO(flota, viaje)
        viajesRepository.save(nuevoViaje)

        val viajeGuardado = Serializer.buildViajeDTO(nuevoViaje, 0)

        return viajeGuardado
    }
}