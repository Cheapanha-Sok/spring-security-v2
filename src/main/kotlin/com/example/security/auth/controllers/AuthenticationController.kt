package com.example.security.auth.controllers

import org.springframework.web.bind.annotation.RestController

import com.example.security.auth.dto.JWTAuthenticationResponse
import com.example.security.auth.exception.BadCredentialsExceptionCustom
import com.example.security.auth.models.User
import com.example.security.auth.permission.UserPrinciple
import com.example.security.auth.request.ChangePassRequest
import com.example.security.auth.request.JWTAuthenticationRequest
import com.example.security.auth.service.AuthenticationService
import com.example.security.utilities.constants.AppConstant
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping(AppConstant.MAIN_PATH+"/auth")
class AuthenticationController(
    private val authenticationService: AuthenticationService,
) {
    @PostMapping("/login")
    fun login(@RequestBody auth: JWTAuthenticationRequest): JWTAuthenticationResponse? {
        return authenticationService.signIn(auth.username, auth.password)
    }
    @PostMapping("/signup")
    fun signup(@RequestBody req: User): User {
        return authenticationService.signUp(req)
    }
    @PostMapping("/refresh-token")
    fun refreshToken(http : HttpServletRequest): JWTAuthenticationResponse {
        return authenticationService.refreshToken(http)
    }
    @GetMapping("/info")
    fun getInfo() : User{
        return authenticationService.getInfo()
    }
    @PostMapping("/change-password")
    fun changePassword(@RequestBody req: ChangePassRequest): Boolean {
        val auth: UserPrinciple?
        try {
            auth = SecurityContextHolder.getContext().authentication.principal as UserPrinciple
        } catch (e: Exception) {
            throw BadCredentialsExceptionCustom("Invalid token", e)
        }
        authenticationService.changePassword(auth.getId(), req)
        SecurityContextHolder.clearContext()
        return true
    }
}