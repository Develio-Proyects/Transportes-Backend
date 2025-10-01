package com.transportes.services

import com.mercadopago.MercadoPagoConfig
import com.mercadopago.client.merchantorder.MerchantOrderClient
import com.mercadopago.client.payment.PaymentClient
import com.mercadopago.client.preference.PreferenceBackUrlsRequest
import com.mercadopago.client.preference.PreferenceClient
import com.mercadopago.client.preference.PreferenceItemRequest
import com.mercadopago.client.preference.PreferenceRequest
import com.mercadopago.resources.payment.Payment
import com.transportes.domain.enums.StateTrip
import com.transportes.domain.trips.Offer
import com.transportes.dto.payment.PaymentInfoDTO
import com.transportes.exceptions.BadRequestException
import com.transportes.exceptions.NotFoundException
import com.transportes.repositories.OfferRepository
import com.transportes.repositories.TripRepository
import jakarta.annotation.PostConstruct
import org.apache.commons.codec.digest.HmacUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class PaymentService {

    @Value("\${mercadopago.access-token}") private lateinit var MP_ACCESS_TOKEN: String
    @Value("\${mercadopago.webhook-key}") private lateinit var MP_WEBHOOK_KEY: String

    @Value("\${spring.url.front}") private lateinit var FRONT_URL: String
    @Value("\${spring.url.api}") private lateinit var API_URL: String

    @Autowired lateinit var offerRepository: OfferRepository
    @Autowired lateinit var tripRepository: TripRepository
    @Autowired lateinit var myUserDetailsService: MyUserDetailsService

    @PostConstruct
    fun initMp() {
        MercadoPagoConfig.setAccessToken(MP_ACCESS_TOKEN)
    }

    fun createPreference(offerId: String): String {
        val offer: Offer = offerRepository.findById(offerId).orElseThrow { NotFoundException("Postulación no encontrada") }
        val currentUser = myUserDetailsService.getCurrentUser() ?: throw NotFoundException("Usuario no autenticado")
        if (offer.trip.state != StateTrip.OPEN) throw BadRequestException("El viaje ya no se encuentra disponible para elegir una postulación")
        if (offer.trip.multiCarrier.id != currentUser.id) throw BadRequestException("No tienes permisos para realizar el pago de esta postulación")
        val tripId: String = offer.trip.id

        val requestItem = PreferenceItemRequest.builder()
            .title("Transporta.com.ar")
            .quantity(1)
            .unitPrice( calculateOfferQuote(offer.offeredPrice) )
            .currencyId("ARS")
            .build()

        val backUrls = PreferenceBackUrlsRequest.builder()
            .success("$FRONT_URL/viajes/$tripId")
            .failure("$FRONT_URL/viajes/$tripId")
            .pending("$FRONT_URL/viajes/$tripId")
            .build()

        val preferenceRequest = PreferenceRequest.builder()
            .items(listOf(requestItem))
            .backUrls(backUrls)
            .autoReturn("approved")
            .externalReference( offerId )
            .notificationUrl("$API_URL/api/payment/webhook")
            .build()

        val preference = PreferenceClient().create(preferenceRequest)

        return preference.initPoint
    }

    fun processPayment(body: Map<String, Any>) {
        var eventType = body["topic"]
        var status = ""
        var offerId = ""

        when (eventType) {
            "merchant_order" -> {
                val merchantId = body["resource"].toString().split("/").last().toLong()
                val merchant = MerchantOrderClient().get(merchantId)
                status = merchant.payments.get(0).status
                offerId = merchant.externalReference
            }
            "payment" -> {
                val paymentId = body["resource"].toString().toLong()
                val payment: Payment = PaymentClient().get(paymentId)
                status = payment.status
                offerId = payment.externalReference
            }
            else -> {
                eventType = body["type"]
                if (eventType == "payment") {
                    val data = body["data"] as Map<*, *>
                    val paymentId = data.get("id").toString().toLong()
                    val payment: Payment = PaymentClient().get(paymentId)
                    status = payment.status
                    offerId = payment.externalReference
                }
            }
        }
        if (status == "approved") assignOfferToTrip(offerId)
    }

    fun assignOfferToTrip(offerId: String) {
        val offer = offerRepository.findById(offerId).orElseThrow { NotFoundException("Postulación no encontrada") }
        val trip = offer.trip
        trip.chosenOffer = offer
        trip.state = StateTrip.ASSIGNED
        tripRepository.save(trip)
    }

    fun validateOrigin(id: String, requestId: String, receivedSignature: String?) {
        if (receivedSignature.isNullOrBlank()) throw BadRequestException("Signature is required")

        val listaSignature = receivedSignature.split(",")
        val timestamp = listaSignature[0].removePrefix("ts=")
        val key = listaSignature[1].removePrefix("v1=")

        val manifest = "id:${id};request-id:${requestId};ts:${timestamp};"
        val cyphedSignature: String = HmacUtils("HmacSHA256", MP_WEBHOOK_KEY).hmacHex(manifest)

        if (key != cyphedSignature) throw IllegalArgumentException("Invalid signature")
    }

    fun getPayments(): List<PaymentInfoDTO> {
        val trips = tripRepository.findWithOfferAssigned()
        return trips.map { trip -> PaymentInfoDTO(
            publisher = trip.multiCarrier.name,
            transport = trip.chosenOffer!!.transport.name,
            origin = trip.origin,
            destination = trip.destination,
            mount = calculateOfferQuote(trip.chosenOffer!!.offeredPrice)
        ) }
    }

    fun calculateOfferQuote(mount: Double): BigDecimal {
        return BigDecimal(mount * 0.02).setScale(1)
    }
}