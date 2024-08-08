package com.example.security.auth.repository

import com.example.security.auth.models.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByUsernameAndStatusIsTrue(username: String?): User?

    fun existsUserByUsername(username: String?): Boolean
}