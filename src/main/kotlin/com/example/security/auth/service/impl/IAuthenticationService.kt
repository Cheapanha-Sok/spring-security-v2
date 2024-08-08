package com.example.security.auth.service.impl

import com.example.security.auth.dto.JWTAuthenticationResponse
import com.example.security.auth.exception.BadCredentialsExceptionCustom
import com.example.security.auth.exception.BadRequestExceptionCustom
import com.example.security.auth.exception.NotFoundExceptionCustom
import com.example.security.auth.jwt_filter.JwtService
import com.example.security.auth.models.User
import com.example.security.auth.permission.UserPrinciple
import com.example.security.auth.repository.UserRepository
import com.example.security.auth.request.ChangePassRequest
import com.example.security.auth.service.AuthenticationService
import com.example.security.auth.service.CustomUserDetailsService
import com.example.security.utilities.annotation.Sl4JLogger.Companion.log
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class IAuthenticationService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val customUserDetailsService: CustomUserDetailsService,
    private val authenticationManager: AuthenticationManager,
    private val jwtService: JwtService
) : AuthenticationService {

    override fun signIn(username: String, password: String): JWTAuthenticationResponse {

        log.info("hello")
        val userDetail = customUserDetailsService.loadUserByUsername(username)
        log.info("load user {} successful", username)

        authenticate(userDetail.username!! , password)
        log.info("verify password successful")

        jwtService.generateToken(userDetail).let {
            return it
        }
    }

    override fun signUp(req: User): User {
        if(userRepository.existsUserByUsername(req.username)){
            throw BadRequestExceptionCustom("Username with ${req.username} already exists")
        }else{
            req.password = passwordEncoder.encode(req.password)
            return userRepository.save(req)
        }
    }

    override fun refreshToken(http: HttpServletRequest): JWTAuthenticationResponse {
        val token: String = jwtService.extractUsername(http.getHeader("Authorization").substringAfter("Bearer "))
        val userDetail = customUserDetailsService.loadUserByUsername(token.split("|")[1])
        log.info("successfully loaded user {}", userDetail.username)
        jwtService.generateRefreshToken(userDetail).let { return it }
    }

    override fun changePassword(userId: Long, req: ChangePassRequest) {
        val user = userRepository.findById(userId).orElseThrow { NotFoundExceptionCustom("User with id $userId does not exist") }
        if (!passwordEncoder.matches(req.currentPassword , user.password))
            throw BadRequestExceptionCustom("Invalid password")

        user.password = passwordEncoder.encode(req.newPassword)
        userRepository.save(user)
    }

    override fun getInfo(): User {
        val auth = SecurityContextHolder.getContext().authentication.principal as UserPrinciple
        return userRepository.findByUsernameAndStatusIsTrue(auth.username) ?: throw BadCredentialsExceptionCustom("User with ${auth.username} not found")
    }

    private fun authenticate(username: String, password: String){
        try {
            log.info("username {} password {}" , username , password)
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(username, password))
        }catch (e : DisabledException){
            throw BadCredentialsExceptionCustom("USER_DISABLED", e)
        }catch (e: BadCredentialsException){
            throw BadCredentialsExceptionCustom("Incorrect username or password" , e)
        }catch (e : Exception) {
            log.error("user error : ${e.message}")
            throw BadCredentialsExceptionCustom("NotFound")
        }
    }
}