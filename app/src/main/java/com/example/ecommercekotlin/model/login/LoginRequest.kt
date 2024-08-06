package com.example.ecommercekotlin.model.login

data class LoginRequest(
    val username : String,
    val password : String,
    val expiresInMins: Int? = null
)


