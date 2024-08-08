package com.example.security.auth.exception

import com.example.security.auth.dto.NotFoundDto
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import java.time.LocalTime

@RestControllerAdvice
class GlobalResponseException {

    @ResponseStatus(HttpStatus.ACCEPTED)
    @ExceptionHandler(NotFoundExceptionCustom::class)
    fun handleNotFoundHandler(ex : NotFoundExceptionCustom) :ResponseEntity<NotFoundDto>{
        val errorMessage = NotFoundDto(
            HttpStatus.NOT_FOUND.value(),
            ex.message,
            localTime = LocalTime.now()
        )
        return ResponseEntity(errorMessage , HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(BadRequestExceptionCustom::class)
    fun handleBadRequestHandler(ex : BadRequestExceptionCustom) :ResponseEntity<NotFoundDto>{
        val errorMessage = NotFoundDto(
            HttpStatus.BAD_REQUEST.value(),
            ex.message,
            localTime = LocalTime.now()
        )
        return ResponseEntity(errorMessage , HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(BadCredentialsExceptionCustom::class)
    fun handleBadCredentialHandler(ex : BadCredentialsExceptionCustom) :ResponseEntity<NotFoundDto>{
        val errorMessage = NotFoundDto(
            HttpStatus.UNAUTHORIZED.value(),
            ex.message,
            localTime = LocalTime.now()
        )
        return ResponseEntity(errorMessage , HttpStatus.UNAUTHORIZED)
    }

}