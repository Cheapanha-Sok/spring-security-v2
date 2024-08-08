package com.example.security.auth.dto

import java.util.Date

data class JWTAuthenticationResponse(
    var accessToken: String? =null,
    var expiresIn: Date? =null,
    var refreshToken: String? =null,
)