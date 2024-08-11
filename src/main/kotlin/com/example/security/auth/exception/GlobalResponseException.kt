package com.example.security.auth.exception

import com.panha.base.response.ResponseDTO
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.*

@RestControllerAdvice
class GlobalResponseException {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundExceptionCustom::class)
    fun handleNotFoundHandler(ex : NotFoundExceptionCustom) :ResponseDTO{
        val responseDTO = ResponseDTO()
        responseDTO.message = ex.message
        responseDTO.code = HttpStatus.NOT_FOUND.value()
        responseDTO.error = HttpStatus.NOT_FOUND.name
        responseDTO.timestamp = Date()
        return responseDTO
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @ExceptionHandler(BadRequestExceptionCustom::class)
    fun handleBadRequestHandler(ex : BadRequestExceptionCustom) :ResponseDTO{
        val responseDTO = ResponseDTO()
        responseDTO.message = ex.message
        responseDTO.code = HttpStatus.BAD_REQUEST.value()
        responseDTO.error = HttpStatus.BAD_REQUEST.name
        responseDTO.timestamp = Date()
        return responseDTO
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsExceptionCustom::class)
    fun handleBadCredentialHandler(ex : BadCredentialsExceptionCustom) :ResponseDTO{
        val responseDTO = ResponseDTO()
        responseDTO.message = ex.message
        responseDTO.code = HttpStatus.UNAUTHORIZED.value()
        responseDTO.error = HttpStatus.UNAUTHORIZED.name
        responseDTO.timestamp = Date()
        return responseDTO
    }

}