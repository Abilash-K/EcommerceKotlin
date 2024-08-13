package com.example.ecommercekotlin.model.user

data class RefreshTokenRequest(
    val refreshToken: String,
    val expiresInMins: Int
)
