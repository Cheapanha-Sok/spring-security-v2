package com.example.security.auth.service

import com.example.security.auth.exception.BadCredentialsExceptionCustom
import com.example.security.auth.exception.NotFoundExceptionCustom
import com.example.security.auth.permission.UserPrinciple
import com.example.security.auth.repository.UserRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.HashSet

@Service
class CustomUserDetailsService (
    private val userRepository: UserRepository,
): UserDetailsService {

    override fun loadUserByUsername(username: String?): UserPrinciple {
        val user = userRepository.findByUsernameAndStatusIsTrue(username) ?: throw BadCredentialsExceptionCustom("User $username not found")
        return UserPrinciple(user.id!! , user.username!! , user.password!! , user.status!! , getAuth(false))
    }

    fun getAuth(auth: Boolean): MutableSet<GrantedAuthority> {
        val authorities: MutableSet<GrantedAuthority> = HashSet()
        authorities.add(SimpleGrantedAuthority(if (auth) "ROLE_MOBILE" else "ROLE_WEB"))
        return authorities
    }
}
