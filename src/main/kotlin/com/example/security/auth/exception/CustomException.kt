package com.example.security.auth.exception

class BadCredentialsExceptionCustom(message: String, e: Exception? = null) : RuntimeException(message, e)

class BadRequestExceptionCustom(message: String, e: Exception? = null) : RuntimeException(message, e)

class NotFoundExceptionCustom(message: String, e: Exception? = null) : RuntimeException(message, e)