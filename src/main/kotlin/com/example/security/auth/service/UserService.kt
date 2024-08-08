package com.example.security.auth.service

import com.example.security.auth.models.User
import com.example.security.auth.request.ChangePassRequest

interface UserService {
    fun addNew(req : User) : User
    fun changePassword(userId : Long , req : ChangePassRequest):Boolean
    fun getUserInfo() : User
}