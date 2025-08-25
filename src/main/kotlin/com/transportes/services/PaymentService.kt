package com.transportes.services

import com.mercadopago.MercadoPagoConfig
import com.mercadopago.client.payment.PaymentClient
import com.mercadopago.client.preference.PreferenceBackUrlsRequest
import com.mercadopago.client.preference.PreferenceClient
import com.mercadopago.client.preference.PreferenceItemRequest
import com.mercadopago.client.preference.PreferenceRequest
import com.mercadopago.resources.payment.Payment
import com.transportes.domain.trips.Offer
import com.transportes.exceptions.BadRequestException
import com.transportes.exceptions.NotFoundException
import com.transportes.repositories.OfferRepository
import jakarta.annotation.PostConstruct
import org.apache.commons.codec.digest.HmacUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class PaymentService {

    @Value("\${SPRING_PROFILES_ACTIVE}") lateinit var PROFILE: String
    @Value("\${mercadopago.access-token}") private lateinit var MP_ACCESS_TOKEN: String
    @Value("\${mercadopago.webhook-key}") private lateinit var MP_WEBHOOK_KEY: String

    @Value("\${spring.url.front}") private lateinit var FRONT_URL: String
    @Value("\${spring.url.api}") private lateinit var API_URL: String

    @Autowired lateinit var offerRepository: OfferRepository

    @PostConstruct
    fun initMp() {
        MercadoPagoConfig.setAccessToken(MP_ACCESS_TOKEN)
    }

    fun createPreference(offerId: String): String {
        val offer: Offer = offerRepository.findById(offerId).orElseThrow { NotFoundException("Postulación no encontrada") }

        val requestItem = PreferenceItemRequest.builder()
            .title("Postulación $offerId")
            .quantity(1)
            .unitPrice(BigDecimal(offer.offeredPrice))
            .currencyId("ARS")
            .build()

        val backUrls = PreferenceBackUrlsRequest.builder()
            .success("$FRONT_URL/success")
            .failure("$FRONT_URL/failure")
            .pending("$FRONT_URL/pending")
            .build()

        val preferenceRequest = PreferenceRequest.builder()
            .items(listOf(requestItem))
            .backUrls(backUrls)
            .autoReturn("approved")
            .externalReference( offerId )
            .notificationUrl("$API_URL/api/payment/webhook")
            .build()

        val preference = PreferenceClient().create(preferenceRequest)

        return if (PROFILE.equals("dev")) preference.sandboxInitPoint
        else preference.initPoint
    }

    fun processPayment(body: Map<String, Any>) {
        val eventType = body["type"] as? String
        val paymentId = (body["data"] as? Map<*, *>)?.get("id")?.toString()

        if (eventType == "payment" && paymentId != null) {
            val payment: Payment = PaymentClient().get(paymentId.toLong())
            val status = payment.status

            if (status == "approved") {
                val offerId = payment.externalReference
                val offer = offerRepository.findById(offerId).orElseThrow { NotFoundException("Postulación no encontrada") }
                offer.trip.chosenOffer = offer
            }
        }
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
}