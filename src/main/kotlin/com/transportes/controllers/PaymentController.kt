package com.transportes.controllers

import com.transportes.dto.payment.PaymentInfoDTO
import com.transportes.services.PaymentService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/payment")
class PaymentController {

    @Autowired lateinit var paymentService: PaymentService

    @PostMapping("/create-preference")
    @Operation(
        summary = "Create payment preference",
        description = "Generates a payment preference in Mercado Pago and returns the init_point URL for redirection"
    )
    fun createPreference(
        @RequestParam offerId: String
    ): ResponseEntity<Map<String, String>> {
        val mpUrl = paymentService.createPreference(offerId)
        return ResponseEntity.ok( mapOf("init_point" to mpUrl) )
    }

    @PostMapping("/webhook")
    @Operation(
        summary = "Endpoint to receive payment notifications",
        description = "Handles incoming webhooks from Mercado Pago for payment status updates"
    )
    fun handleWebhook(
        @RequestHeader("x-signature") receivedSignature: String,
        @RequestHeader("x-request-id") requestId: String,
        @RequestBody body: Map<String, Any>,
        @RequestParam(name = "data.id") idData: String
    ): ResponseEntity<Void> {
        //paymentService.validateOrigin(idData, requestId, receivedSignature)
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