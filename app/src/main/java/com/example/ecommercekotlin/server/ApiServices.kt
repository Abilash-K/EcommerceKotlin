package com.example.ecommercekotlin.server

import com.example.ecommercekotlin.model.category.Category
import com.example.ecommercekotlin.model.login.LoginRequest
import com.example.ecommercekotlin.model.login.LoginResponse
import com.example.ecommercekotlin.model.product.Product
import com.example.ecommercekotlin.model.product.ProductResponse
import com.example.ecommercekotlin.model.user.TokenResponse
import com.example.ecommercekotlin.model.user.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiServices {

    @POST("auth/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @GET("products/categories")
    fun getCategories(): Call<List<Category>>

    @GET("user/me")
    fun getUserDetails(@Header("Authorization") token: String): Call<User>

    @POST("auth/refresh")
    fun refreshToken(@Body request: Map<String, Any>): Call<TokenResponse>

}


