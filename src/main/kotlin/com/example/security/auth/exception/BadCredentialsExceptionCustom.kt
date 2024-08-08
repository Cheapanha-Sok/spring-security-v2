package com.example.security.auth.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus


@ResponseStatus(HttpStatus.UNAUTHORIZED)
class BadCredentialsExceptionCustom(message: String, e: Exception? = null) : RuntimeException(message, e)