package com.transportes.services

import com.transportes.domain.enums.StateTrip
import com.transportes.domain.trips.Offer
import com.transportes.domain.users.Transport
import com.transportes.dto.trip.OfferDTO
import com.transportes.exceptions.BadRequestException
import com.transportes.exceptions.NotFoundException
import com.transportes.repositories.OfferRepository
import com.transportes.repositories.TripRepository
import com.transportes.utils.Serializer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class OfferService {
    @Autowired lateinit var offerRepository: OfferRepository
    @Autowired lateinit var userDetailsService: MyUserDetailsService
    @Autowired lateinit var tripRepository: TripRepository
    @Autowired lateinit var paymentService: PaymentService

    fun getQuoteOffer(idOffer: String): BigDecimal {
        val offer = offerRepository.findById(idOffer).orElseThrow { NotFoundException("Postulación con id $idOffer no fue encontrada") }
        return paymentService.calculateOfferQuote(offer.offeredPrice)
    }

    fun cancelOffer(idOffer: String) {
        val offer = offerRepository.findById(idOffer).orElseThrow { NotFoundException("No se encontro la postulación") }
        val user = userDetailsService.getCurrentUser() ?: throw BadRequestException("Usuario no autenticado")
        if (offer.transport.id != user.id) throw BadRequestException("No puedes cancelar una postulación que no es tuya")
        if (offer.trip.state != StateTrip.OPEN) throw BadRequestException("La postulación no puede ser cancelada porque el viaje ya no está en subasta")
        offerRepository.deleteById(offer.id)
    }

    fun offerTrip(idTrip: String, mount: Double): OfferDTO {
        validateOffer(idTrip, mount)
        val user = userDetailsService.getCurrentUser() ?: throw BadRequestException("Usuario no autenticado")
        val trip = tripRepository.findById(idTrip).orElseThrow { NotFoundException("Viaje con id $idTrip no fue encontrado") }

        if (user.id == trip.multiCarrier.id) throw BadRequestException("No puedes ofertar en un viaje que creaste")

        val newOffer = Offer(
            trip = trip,
            transport = user as Transport,
            offeredPrice = mount
        )

        offerRepository.save(newOffer)
        return Serializer.buildOfferDTO(newOffer)
    }

    fun validateOffer(tripId: String, mount: Double) {
        val offers = offerRepository.findOfferByMinorOffer(tripId)
        if (offers.isNotEmpty()) {
            val offer = offers.first()
            if (mount >= offer.offeredPrice) {
                throw BadRequestException("La oferta debe ser menor a ${offer.offeredPrice}")
            }
        }
    }
}