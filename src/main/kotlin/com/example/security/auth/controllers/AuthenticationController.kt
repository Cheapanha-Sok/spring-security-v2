package com.example.security.auth.controllers

import org.springframework.web.bind.annotation.RestController

import com.example.security.auth.exception.BadCredentialsExceptionCustom
import com.example.security.auth.models.User
import com.example.security.auth.permission.UserPrinciple
import com.example.security.auth.request.ChangePassRequest
import com.example.security.auth.request.JWTAuthenticationRequest
import com.example.security.auth.service.AuthenticationService
import com.example.security.utilities.constants.AppConstant
import com.panha.base.response.JSONFormat
import com.panha.base.response.ResponseDTO
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping(AppConstant.MAIN_PATH+"/auth")
class AuthenticationController(
    private val authenticationService: AuthenticationService,
    private val jsonFormat: JSONFormat
) {
    @PostMapping("/login")
    fun login(@RequestBody auth: JWTAuthenticationRequest): ResponseDTO {
        return jsonFormat.respondObj(authenticationService.signIn(auth.username, auth.password) , HttpStatus.OK , "Login successful")
    }
    @PostMapping("/signup")
    fun signup(@RequestBody req: User): ResponseDTO {
        return jsonFormat.respondObj(authenticationService.signUp(req) , HttpStatus.OK , "Sign up successful")
    }
    @PostMapping("/refresh-token")
    fun refreshToken(http : HttpServletRequest): ResponseDTO {
        return jsonFormat.respondObj(authenticationService.refreshToken(http) ,HttpStatus.OK , "Refresh token successful")
    }
    @GetMapping("/info")
    fun getInfo() :ResponseDTO {
        return jsonFormat.respondObj(authenticationService.getInfo() , HttpStatus.OK , "fetch user info")
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