package com.transportes.controllers

import com.transportes.dto.ResponseWithMessageDTO
import com.transportes.services.ExampleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/example")
class ExampleController {
    @Autowired lateinit var exampleService: ExampleService

    @GetMapping
    fun example(): ResponseWithMessageDTO {
        val ok = exampleService.getOk()
        return ResponseWithMessageDTO(ok)
    }
}