package com.example.security.auth.service.impl

import com.example.security.auth.exception.BadRequestExceptionCustom
import com.example.security.auth.exception.NotFoundExceptionCustom
import com.example.security.auth.models.User
import com.example.security.auth.permission.UserPrinciple
import com.example.security.auth.repository.UserRepository
import com.example.security.auth.request.ChangePassRequest
import com.example.security.auth.service.UserService
import com.example.security.utilities.annotation.Sl4JLogger.Companion.log
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class IUserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : UserService {

    override fun addNew(req: User): User {
        if(userRepository.existsUserByUsername(req.username)){
            throw BadRequestExceptionCustom("Username with ${req.username} already exists")
        }else{
            req.password = passwordEncoder.encode(req.password)
            return userRepository.save(req)
        }
    }

    override fun changePassword(userId: Long, req: ChangePassRequest): Boolean {
        val user = userRepository.findById(userId)
            .orElseThrow { NotFoundExceptionCustom("User with id $userId not found") }

        log.info("user {}", userId)

        if (!passwordEncoder.matches(req.currentPassword, user.password))
            throw BadRequestExceptionCustom("Current password is invalid")

        user.password = passwordEncoder.encode(req.newPassword)
        userRepository.save(user)

        return true
    }


    override fun getUserInfo(): User {
        val auth = SecurityContextHolder.getContext().authentication.principal as UserPrinciple
        return userRepository.findById(auth.getId()).orElseThrow { NotFoundExceptionCustom("User with id ${auth.getId()} not found") }
    }
}