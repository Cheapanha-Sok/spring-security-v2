package com.example.security.auth.jwt_filter

import com.example.security.auth.dto.JWTAuthenticationResponse
import com.example.security.auth.permission.UserPrinciple
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.security.Key
import java.util.*
import java.util.function.Function

@Service
class JwtService {
    @Value("\${application.security.jwt.secret-key}")
    private val secretKey: String? = null

    @Value("\${application.security.jwt.expiration}")
    private val jwtExpiration: Long = 30

    @Value("\${application.security.jwt.refresh-token.expiration}")
    private val refreshExpiration: Long = 60

    val jwtTokenValidity = jwtExpiration * 24 * 60 * 60 * 1000
    val jwtTokenRefreshValidity = refreshExpiration * 24 * 60 * 60 * 1000


    fun generateToken(userPrinciple: UserPrinciple): JWTAuthenticationResponse {
        val expired = Date(System.currentTimeMillis() + jwtTokenValidity)
        val token = buildToken(HashMap(), userPrinciple, expired)
        return JWTAuthenticationResponse(token,expired,null)
    }

    fun generateRefreshToken(userPrinciple: UserPrinciple): JWTAuthenticationResponse {
        val expired = Date(System.currentTimeMillis() + jwtTokenRefreshValidity)
        val token = buildToken(HashMap(), userPrinciple, expired)
        return JWTAuthenticationResponse(token,expired,null)
    }


    //Do extract UserName From Token
    fun extractUsername(token: String?): String {
        return extractClaim(token) { obj: Claims -> obj.subject }
    }



    //Do Validate Token with Username
    fun isTokenValid(token: String?, userPrinciple: UserPrinciple): Boolean {

        val username = extractUsername(token)
        val principle = "${userPrinciple.getId()}|${userPrinciple.username}|${userPrinciple.isEnabled}"

        return (username == principle) && !isTokenExpired(token) && userPrinciple.isEnabled
    }

    // inner fun support

    private fun buildToken(extraClaims: Map<String?, Any?>, userPrinciple: UserPrinciple, expiration: Date): String {

        return Jwts
            .builder()
            .setClaims(extraClaims)
            .setSubject("${userPrinciple.getId()}|${userPrinciple.username}|${userPrinciple.isEnabled}")
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(expiration)
            .signWith(signInKey, SignatureAlgorithm.HS256)
            .compact()
    }

    private fun isTokenExpired(token: String?): Boolean {
        return extractExpiration(token).before(Date())
    }

    private fun extractExpiration(token: String?): Date {
        return extractClaim(token) { obj: Claims -> obj.expiration }
    }

    private fun <T> extractClaim(token: String?, claimsResolver: Function<Claims, T>): T {
        val claims = extractAllClaims(token)
        return claimsResolver.apply(claims)
    }

    private fun extractAllClaims(token: String?): Claims {
        return Jwts
            .parserBuilder()
            .setSigningKey(signInKey)
            .build()
            .parseClaimsJws(token)
            .body
    }

    private val signInKey: Key
        get() {
        val keyBytes = Decoders.BASE64.decode(secretKey)
        return Keys.hmacShaKeyFor(keyBytes)
    }
}