package com.example.security.auth.request

data class JWTAuthenticationRequest(
    var username: String,
    var password: String
)