package com.transportes.controllers

import com.transportes.dto.ResponseWithMessageDTO
import com.transportes.dto.trip.OfferDTO
import com.transportes.services.OfferService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal

@RestController
@RequestMapping("/api/offer")
class OfferController {
    @Autowired lateinit var offerService: OfferService

    @DeleteMapping("/{idOffer}")
    @Operation(
        summary = "Cancel offer",
        description = "Cancel an offer if the associated trip is still open for bidding"
    )
    fun cancelOffer(
        @PathVariable(value = "idOffer") idOffer: String
    ): ResponseWithMessageDTO {
        offerService.cancelOffer(idOffer)
        return ResponseWithMessageDTO("Postulación cancelada con éxito")
    }

    @PostMapping("/{idTrip}")
    @Operation(
        summary = "Solocarrier or Multicarrier offer trip",
        description = "Submits an offer for a specific trip"
    )
    fun offerTrip(
        @PathVariable idTrip: String,
        @RequestParam mount: Double,
    ): OfferDTO {
        val newOffer = offerService.offerTrip(idTrip, mount)
        return newOffer
    }

    @GetMapping("/quote/{idOffer}")
    @Operation(
        summary = "Get quote for offer",
        description = "Retrieves the quote for a specific offer by its ID."
    )
    fun getQuoteOffer(
        @PathVariable idOffer: String
    ): ResponseEntity<Map<String, BigDecimal>> {
        val quote = offerService.getQuoteOffer(idOffer)
        return ResponseEntity.ok( mapOf("tarifa" to quote) )
    }
}