package com.example.ecommercekotlin.server

import com.example.ecommercekotlin.model.login.LoginRequest
import com.example.ecommercekotlin.model.login.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiServices {

    @POST("auth/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

}