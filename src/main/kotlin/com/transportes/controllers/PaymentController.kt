package com.transportes.controllers

import com.transportes.dto.payment.PaymentInfoDTO
import com.transportes.services.PaymentService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/payment")
class PaymentController {

    @Autowired lateinit var paymentService: PaymentService
    @Value("\${spring.url.front}") private lateinit var FRONT_URL: String

    @PostMapping("/create-preference")
    @Operation(
        summary = "Create payment preference",
        description = "Generates a payment preference in Mercado Pago and returns the init_point URL for redirection"
    )
    fun createPreference(
        @RequestParam offerId: String
    ): ResponseEntity<Map<String, String>> {
        //val mpUrl = paymentService.createPreference(offerId)
        //return ResponseEntity.ok( mapOf("init_point" to mpUrl) )
        paymentService.temporlyProcessPayment(offerId)
        return ResponseEntity.ok( mapOf("init_point" to FRONT_URL) )
    }

    @PostMapping("/webhook")
    @Operation(
        summary = "Endpoint to receive payment notifications",
        description = "Handles incoming webhooks from Mercado Pago for payment status updates"
    )
    fun handleWebhook(
        @RequestHeader("x-signature") receivedSignature: String,
        @RequestHeader("x-request-id") requestId: String,
        @RequestBody body: Map<String, Any>
    ): ResponseEntity<Void> {
        // TODO
        // 1. El validateOrigin no funciona cuando pruebo un pago, la peticion llega pero no coincide la key con el cyphedSignature. Si funciona cuando mando un webhook de prueba desde la seccion de mercadopago developers.
        // 2. Note que no siempre viene un aviso de tipo payment, quiza sea por eso que no coincide. Hay que ver que viene y controlarlo.
        // 3. Una vez que pueda pasar la validacion, vincular la offer con el viaje. En el externalId del pago esta el id de la offer.
        paymentService.validateOrigin(body["id"].toString(), requestId, receivedSignature)
        paymentService.processPayment(body)
        return ResponseEntity.ok().build()
    }

    @GetMapping
    @Operation(
        summary = "Get history payments",
        description = "Retrieve all payments made in the system"
    )
    fun getPayments(): List<PaymentInfoDTO> {
        return paymentService.getPayments()
    }
}