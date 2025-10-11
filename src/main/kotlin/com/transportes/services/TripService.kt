package com.transportes.services

import com.transportes.domain.enums.CargoType
import com.transportes.domain.enums.StateTrip
import com.transportes.domain.trips.Dimensions
import com.transportes.domain.trips.Trip
import com.transportes.dto.trip.NewTripDTO
import com.transportes.dto.trip.TripToAdminDTO
import com.transportes.dto.trip.TripDTO
import com.transportes.dto.trip.TripDetailDTO
import com.transportes.dto.trip.PostDTO
import com.transportes.dto.trip.UpdateTripDTO
import com.transportes.exceptions.BadRequestException
import com.transportes.exceptions.InvalidCredentialsException
import com.transportes.exceptions.NotFoundException
import com.transportes.repositories.MultiCarrierRepository
import com.transportes.repositories.OfferRepository
import com.transportes.repositories.TripRepository
import com.transportes.repositories.UserRepository
import com.transportes.utils.Serializer
import com.transportes.utils.Serializer.buildTripToAdminDTO
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDateTime
import kotlin.String

@Service
class TripService {
    @Autowired lateinit var tripRepository: TripRepository
    @Autowired lateinit var userRepository: UserRepository
    @Autowired lateinit var multiCarrierRepository: MultiCarrierRepository
    @Autowired lateinit var offerRepository: OfferRepository
    @Autowired lateinit var userDetailsService: MyUserDetailsService

    fun getPostedTrips(token: String?, page: Int, size: Int): Page<PostDTO> {
        val page: Pageable = Pageable.ofSize(size).withPage(page)
        val user = if (token != null) {
            try { userDetailsService.getUserByToken(token) }
            catch (e: InvalidCredentialsException) { null }
        } else null

        val tripList = tripRepository.getTripsByState(StateTrip.OPEN, page)
        return tripList.map {
            val offersCount = offerRepository.getOffersCountByTripId(it.id)
            val myPost = if (user != null) it.multiCarrier.id == user.id else false
            Serializer.buildPostedTripsDTO(it, offersCount, myPost)
        }
    }

    fun getUserPostedTrips(page: Int, size: Int): Page<TripDTO> {
        val page: Pageable = Pageable.ofSize(size).withPage(page)
        val user = userDetailsService.getCurrentUser() ?: throw BadRequestException("Usuario no autenticado")

        val tripList = tripRepository.getTripsByMultiCarrierEmail(user.email, page)
        return tripList.map {
            val offersCount = offerRepository.getOffersCountByTripId(it.id)
            Serializer.buildTripDTO(it, offersCount)
        }
    }

    fun getUserTrips(page: Int, size: Int): Page<TripDTO> {
        val pageable: Pageable = Pageable.ofSize(size).withPage(page)
        val user = userDetailsService.getCurrentUser() ?: throw BadRequestException("Usuario no autenticado")

        val appliedTripIds = tripRepository.getTripsByAppliedUserEmail(user.email).map { it.id }
        val assignedTripIds = tripRepository.getTripsByAssignedUserEmail(user.email).map { it.id }
        val idsList = (appliedTripIds + assignedTripIds).distinctBy { it }

        val tripList = tripRepository.findAllByIdList(idsList, pageable)
            .map {
                val offersCount = offerRepository.getOffersCountByTripId(it.id)
                Serializer.buildTripDTO(it, offersCount)
            }

        return tripList
    }

    fun getTripDetail(id: String): TripDetailDTO {
        val trip: Trip = tripRepository.findById(id).orElseThrow { NotFoundException("Viaje con id $id no fue encontrado") }
        val offers = offerRepository.findAllAscendingByTripId(trip.id)

        var myPost = false
        try {
            val user = userDetailsService.getCurrentUser()
            myPost = if (user != null) trip.multiCarrier.id == user.id else false
        } catch (e: InvalidCredentialsException) {}

        return Serializer.buildTripDetailDTO(trip, offers, myPost)
    }

    fun getTripsToAdmin(page: Int, size: Int): Page<TripToAdminDTO> {
        val page: Pageable = Pageable.ofSize(size).withPage(page)
        val trips = tripRepository.findAll(page)
        return trips.map { trip -> buildTripToAdminDTO(trip) }
    }

    fun createTrip(newTripDTO: NewTripDTO): TripDTO {
        val user = userDetailsService.getCurrentUser() ?: throw BadRequestException("Usuario no autenticado")
        val multiCarrier = multiCarrierRepository.findById(user.id).orElseThrow { NotFoundException("Usuario no encontrado") }

        val newTrip: Trip = Serializer.buildTripByNewTripDTO(multiCarrier, newTripDTO)
        tripRepository.save(newTrip)

        val tripSaved = Serializer.buildTripDTO(newTrip, 0)
        return tripSaved
    }

    fun changeTripState(tripId: String, newState: StateTrip) {
        val trip = tripRepository.findById(tripId).orElseThrow { NotFoundException("Viaje no encontrado") }
        trip.state = newState
        tripRepository.save(trip)
    }

    @Transactional
    fun deleteTrip(idTrip: String) {
        val trip = tripRepository.findById(idTrip).orElseThrow {NotFoundException("Publicación no encontrada")}

        if (trip.state != StateTrip.OPEN){
            throw BadRequestException("El estado de la publicación no es válido")
        } else{
            val allOffers = offerRepository.getOffersOfTrip(trip.id)
            for (offer in allOffers){
                offerRepository.deleteById(offer.id)
            }
            tripRepository.deleteById(trip.id)
        }
    }

    fun updateTrip(idTrip: String, updateTripDTO: UpdateTripDTO): TripDetailDTO {
        val trip = tripRepository.findById(idTrip).orElseThrow{ NotFoundException ("Publicación no encontrada")}

        if (trip.state != StateTrip.OPEN){
            throw BadRequestException("No se puede modificar la publicación")
        }

        if (updateTripDTO.departureDate.isBefore(LocalDateTime.now())) {
            throw BadRequestException("La fecha de salida debe ser posterior al día de hoy")
        }
        trip.updateTrip(updateTripDTO)
        tripRepository.save(trip)

        return getTripDetail(trip.id)
    }
}