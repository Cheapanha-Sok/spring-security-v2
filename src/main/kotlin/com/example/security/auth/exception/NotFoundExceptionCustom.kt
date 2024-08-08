package com.example.security.auth.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class NotFoundExceptionCustom(message: String, e: Exception? = null) : RuntimeException(message, e)