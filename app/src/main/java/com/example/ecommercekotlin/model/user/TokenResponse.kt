package com.example.ecommercekotlin.model.user

data class TokenResponse(
    val token: String,
    val refreshToken: String
)