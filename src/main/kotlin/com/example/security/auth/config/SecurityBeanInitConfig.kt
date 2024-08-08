package com.example.security.auth.config

import com.example.security.auth.exception.BadCredentialsExceptionCustom
import com.example.security.auth.exception.NotFoundExceptionCustom
import com.example.security.auth.permission.UserPrinciple
import com.example.security.auth.repository.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class SecurityBeanInitConfig(
    private val userRepository: UserRepository
) {
    @Bean
    fun userDetailsService(): UserDetailsService {
        return UserDetailsService { username: String? ->
            val user = userRepository.findByUsernameAndStatusIsTrue(username) ?: throw BadCredentialsExceptionCustom("User with $username Not Found")
            UserPrinciple(user.id!!, user.username, user.password, user.status!!, null)
        }
    }

    @Bean
    fun authenticationProvider(): DaoAuthenticationProvider {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(userDetailsService())
        authProvider.setPasswordEncoder(passwordEncoder())
        return authProvider
    }

    @Bean
    fun authenticationManager(authConfig: AuthenticationConfiguration): AuthenticationManager {
        return authConfig.authenticationManager
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}
