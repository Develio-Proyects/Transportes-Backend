package com.transportes.exceptions

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.multipart.MaxUploadSizeExceededException

@ControllerAdvice
class CustomExceptionHandler {
    @Value("\${spring.servlet.multipart.max-file-size}") private lateinit var maxSize: String

    // PERSONALIZADAS

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(ex: BadRequestException): ResponseEntity<Map<String, String>> {
        val message = ex.message ?: "Ha ocurrido un error con la solicitud."
        val errorDetails = mapOf(
            "error" to "Solicitud incorrecta",
            "message" to message
        )
        return ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(ex: NotFoundException): ResponseEntity<Map<String, String>> {
        val message = ex.message ?: "Un recurso solicitado no fue encontrado."
        val errorDetails = mapOf(
            "error" to "Recurso no encontrado",
            "message" to message
        )
        return ResponseEntity(errorDetails, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(InvalidCredentialsException::class)
    fun handleInvalidCredentialsException(ex: InvalidCredentialsException): ResponseEntity<Map<String, String>> {
        val message = ex.message ?: "Credenciales inválidas."
        val errorDetails = mapOf(
            "error" to "No autorizado",
            "message" to message
        )
        return ResponseEntity(errorDetails, HttpStatus.UNAUTHORIZED)
    }

    // OTRAS

    /**
     * Esta excepción se lanza cuando se intenta subir un archivo que excede el tamaño máximo permitido
     * (configurado en application.properties)
     */
    @ExceptionHandler(MaxUploadSizeExceededException::class)
    fun handleMaxSizeException(ex: MaxUploadSizeExceededException): ResponseEntity<Map<String, String>> {
        val errorDetails = mapOf(
            "error" to "Imagen demasiada grande",
            "message" to "El archivo excede el tamaño máximo permitido ($maxSize)"
        )
        return ResponseEntity(errorDetails, HttpStatus.PAYLOAD_TOO_LARGE)
    }

    @ExceptionHandler(MissingServletRequestParameterException::class)
    fun handleMissingParams(ex: MissingServletRequestParameterException): ResponseEntity<Map<String, String>> {
        val errorDetails = mapOf( "error" to "Parámetros faltantes" )
        return ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST)
    }

    /**
     * Esta excepción se lanza cuando por ejemplo no se cumple alguna validación @field de los DTOs
     */
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<Map<String, String>> {
        val errorDetails = mapOf( "error" to "Parámetros inválidos" )
        return ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleMethodArgumentTypeMismatchException(ex: MethodArgumentTypeMismatchException): ResponseEntity<Map<String, String>> {
        val errorDetails = mapOf( "error" to "Parámetros inválidos" )
        return ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST)
    }
}