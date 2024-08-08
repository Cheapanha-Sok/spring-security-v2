package com.example.security.auth.service

import com.example.security.auth.dto.JWTAuthenticationResponse
import com.example.security.auth.models.User
import jakarta.servlet.http.HttpServletRequest

interface AuthenticationService {
    fun signIn(username : String , password : String ): JWTAuthenticationResponse
    fun signUp(req : User) : User
    fun refreshToken(http : HttpServletRequest):JWTAuthenticationResponse
    fun getInfo():User
}