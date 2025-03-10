package com.example.ecommercekotlin.model.login

data class LoginResponse(
    val id : Int,
    val username : String,
    val email : String,
    val firstName : String,
    val lastName : String,
    val gender : String,
    val image : String,
    val accessToken : String,
    val refreshToken : String
)