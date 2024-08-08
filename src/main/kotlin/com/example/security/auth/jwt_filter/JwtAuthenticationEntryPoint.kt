package com.example.security.auth.jwt_filter

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class JwtAuthenticationEntryPoint : AuthenticationEntryPoint {

    override fun commence(request: HttpServletRequest?, response: HttpServletResponse, authException: AuthenticationException?) {
        val invalid = request?.getAttribute("invalid") as? String
        val invalidApiKey = request?.getAttribute("invalid apiKey") as? String
        val expired = request?.getAttribute("expired") as? String
        val message = authException?.message

        // Logging for debugging
        println("Invalid: $invalid")
        println("Invalid API Key: $invalidApiKey")
        println("Expired: $expired")
        println("AuthenticationException message: $message")

        when {
            invalid != null -> UnauthorizedHandler.invalidToken(response)
            expired != null -> UnauthorizedHandler.expiredToken(response)
            invalidApiKey != null -> UnauthorizedHandler.invalidX_Api_Key(response)
            else -> UnauthorizedHandler.invalidToken(response)
        }
    }
}
