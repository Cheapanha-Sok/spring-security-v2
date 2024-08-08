package com.example.security.auth.permission

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserPrinciple(
    private var id: Long,
    private var name: String?,
    private var pass: String?,
    private var enable: Boolean,
    private val auth: MutableCollection<out GrantedAuthority>?

): UserDetails {

    fun getId(): Long {
        return this.id
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority>? {
        return this.auth
    }

    override fun isEnabled(): Boolean {
        return this.enable
    }

    override fun getUsername(): String? {
        return this.name
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun getPassword(): String? {
        return this.pass
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }
}