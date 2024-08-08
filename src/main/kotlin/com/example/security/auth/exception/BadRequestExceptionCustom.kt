package com.example.security.auth.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus


@ResponseStatus(HttpStatus.BAD_REQUEST)
class BadRequestExceptionCustom(message : String , e : Exception? =null) : RuntimeException(message , e)