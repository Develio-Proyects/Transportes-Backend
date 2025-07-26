package com.transportes.services

import com.transportes.domain.enums.StateTrip
import com.transportes.domain.viajes.Trip
import com.transportes.dto.viajes.NewTripDTO
import com.transportes.dto.viajes.TripToAdminDTO
import com.transportes.dto.viajes.TripDTO
import com.transportes.dto.viajes.TripDetailDTO
import com.transportes.dto.viajes.PostDTO
import com.transportes.exceptions.BadRequestException
import com.transportes.exceptions.InvalidCredentialsException
import com.transportes.exceptions.NotFoundException
import com.transportes.repositories.MultiCarrierRepository
import com.transportes.repositories.OfferRepository
import com.transportes.repositories.TripRepository
import com.transportes.utils.Serializer
import com.transportes.utils.Serializer.buildTripToAdminDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class TripService {
    @Autowired lateinit var tripRepository: TripRepository
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
        return Serializer.buildTripDetailDTO(trip, offers)
    }

    fun getQuoteOffer(idOffer: String): Double {
        val offer = offerRepository.findById(idOffer).orElseThrow { NotFoundException("Postulación con id $idOffer no fue encontrada") }
        return offer.offeredPrice * 0.2
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
}