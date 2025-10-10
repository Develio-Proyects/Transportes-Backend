package com.transportes.utils

import com.transportes.domain.Message
import com.transportes.domain.Truck
import com.transportes.domain.documents.Document
import com.transportes.domain.enums.StateTrip
import com.transportes.domain.users.MultiCarrier
import com.transportes.domain.trips.Offer
import com.transportes.domain.trips.Trip
import com.transportes.domain.users.Employee
import com.transportes.domain.users.User
import com.transportes.dto.DocumentDTO
import com.transportes.dto.employee.EmployeeDTO
import com.transportes.dto.chat.ExitChatMessageDTO
import com.transportes.dto.login.LoginResponseDTO
import com.transportes.dto.offer.OfferDTO
import com.transportes.dto.truck.TruckDTO
import com.transportes.dto.trip.*
import com.transportes.dto.user.InfoPostulantDTO
import java.time.LocalDateTime

object Serializer {
    fun buildPostedTripsDTO(trip: Trip, cantPostulaciones: Long, miPublicacion: Boolean): PostDTO {
        val postedSince: String = DateUtils.spentTime(trip.postedDate)
        return PostDTO(
            trip.id,
            trip.origin,
            trip.destination,
            trip.departureDate,
            trip.cargoType.frontName,
            trip.basePrice,
            cantPostulaciones,
            postedSince,
            miPublicacion
        )
    }

    fun buildTripDTO(trip: Trip, cantPostulaciones: Long): TripDTO {
        val publicadoHace: String = DateUtils.spentTime(trip.postedDate)
        return TripDTO(
            trip.id,
            trip.origin,
            trip.destination,
            trip.state.frontName,
            trip.departureDate,
            trip.cargoType.frontName,
            trip.basePrice,
            cantPostulaciones,
            publicadoHace
        )
    }

    fun buildTripToAdminDTO(trip: Trip): TripToAdminDTO {
        return TripToAdminDTO(
            trip.origin,
            trip.destination,
            trip.departureDate,
            trip.basePrice,
            trip.chosenOffer?.transport?.name,
            trip.multiCarrier.razonSocial,
            trip.state.frontName
        )
    }

    fun buildLoginResponseDTO(userId: String, name: String, email: String, role: String, token: String): LoginResponseDTO {
        return LoginResponseDTO(
            userId,
            name,
            email,
            role,
            token
        )
    }

    fun buildOfferDTO(offer: Offer): OfferDTO {
        return OfferDTO(
            offer.id,
            offer.transport.name,
            offer.transport.id,
            offer.offeredPrice
        )
    }

    fun buildTripDetailDTO(trip: Trip, postulaciones: List<Offer>, myPost: Boolean): TripDetailDTO {
        val ofertaMasBaja = postulaciones.minByOrNull{ it.offeredPrice }?.offeredPrice
        val listaPostulacionesDTO = postulaciones.map { buildOfferDTO(it) }
        return TripDetailDTO(
            trip.multiCarrier.razonSocial,
            trip.departureDate,
            trip.state.frontName,
            trip.origin,
            trip.destination,
            trip.observations,
            trip.cargoType.frontName,
            trip.weight,
            trip.dimensions,
            trip.basePrice,
            ofertaMasBaja,
            listaPostulacionesDTO,
            myPost
        )
    }

    fun buildTruckDTO(truck: Truck): TruckDTO {
        return TruckDTO(
            truck.id,
            truck.brand,
            truck.model,
            truck.patent
        )
    }

    fun buildDocumentDTO(document: Document): DocumentDTO {
        return DocumentDTO(
            document.id,
            document.getIdUser(),
            document.getNameUser(),
            document.getLastnameUser(),
            document.name,
            document.linkImage!!
        )
    }

    fun buildEmployeeDTO(employee: Employee): EmployeeDTO {
        return EmployeeDTO(
            employee.id,
            employee.name,
            employee.lastname
        )
    }

    fun buildTripByNewTripDTO(multiCarrier: MultiCarrier, viaje: NewTripDTO): Trip {
        return Trip(
            multiCarrier = multiCarrier,
            chosenOffer = null,
            state = StateTrip.OPEN,
            origin = viaje.origin,
            destination = viaje.destination,
            departureDate = viaje.departureDate,
            postedDate = LocalDateTime.now(),
            basePrice = viaje.basePrice,
            cargoType = viaje.cargoType,
            weight = viaje.weight,
            dimensions = viaje.dimensions,
            observations = viaje.observations
        )
    }

    fun buildExitChatMessageDTOByMessage(message: Message): ExitChatMessageDTO {
        return ExitChatMessageDTO(
            message.transmitterId,
            message.timestamp,
            message.message
        )
    }

    fun buildUserCompletedTripDTO(trip: Trip): UserCompletedTripDTO {
        return UserCompletedTripDTO(
            trip.id,
            trip.origin,
            trip.destination,
            trip.state.frontName,
            trip.departureDate,
            trip.cargoType.frontName
        )
    }

    fun buildInfoPostulantDTO(user: User, documents: List<Document>, trucks: List<Truck>, completedTrips: List<Trip>): InfoPostulantDTO {
        val documentsDTO = documents.map { buildDocumentDTO(it) }
        val trucksDTO = trucks.map { buildTruckDTO(it) }
        val completedTripsDTO = completedTrips.map { buildUserCompletedTripDTO(it) }
        return InfoPostulantDTO(
            user.name,
            user.role.frontName,
            user.email,
            documentsDTO,
            trucksDTO,
            completedTripsDTO
        )
    }
}