package com.transportes.utils

import com.transportes.domain.Truck
import com.transportes.domain.documents.Document
import com.transportes.domain.enums.StateTrip
import com.transportes.domain.users.MultiCarrier
import com.transportes.domain.trips.Offer
import com.transportes.domain.trips.Trip
import com.transportes.dto.DocumentDTO
import com.transportes.dto.login.LoginResponseDTO
import com.transportes.dto.truck.TruckDTO
import com.transportes.dto.trip.*
import java.time.LocalDateTime

object Serializer {
    fun buildPostedTripsDTO(trip: Trip, cantPostulaciones: Long, miPublicacion: Boolean): PostDTO {
        val postedSince: String = DateUtils.spentTime(trip.postedDate)
        return PostDTO(
            trip.id,
            trip.origin,
            trip.destination,
            trip.departureDate,
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

    fun buildLoginResponseDTO(rol: String, nombre: String, token: String): LoginResponseDTO {
        return LoginResponseDTO(
            rol,
            nombre,
            token
        )
    }

    fun buildOfferDTO(offer: Offer): OfferDTO {
        return OfferDTO(
            offer.id,
            offer.transport.name,
            offer.offeredPrice
        )
    }

    fun buildTripDetailDTO(trip: Trip, postulaciones: List<Offer>): TripDetailDTO {
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
            listaPostulacionesDTO
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
            document.getIdUser(),
            document.name,
            document.linkImage
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
}