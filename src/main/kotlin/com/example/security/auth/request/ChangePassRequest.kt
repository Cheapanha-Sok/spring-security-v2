package com.example.security.auth.request

data class ChangePassRequest(
    var currentPassword: String,
    var newPassword: String
)
