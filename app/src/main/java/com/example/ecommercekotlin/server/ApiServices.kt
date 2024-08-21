package com.example.ecommercekotlin.server

import com.example.ecommercekotlin.model.category.Category
import com.example.ecommercekotlin.model.login.LoginRequest
import com.example.ecommercekotlin.model.login.LoginResponse
import com.example.ecommercekotlin.model.product.Product
import com.example.ecommercekotlin.model.product.ProductResponse
import com.example.ecommercekotlin.model.user.RefreshTokenRequest
import com.example.ecommercekotlin.model.user.TokenResponse
import com.example.ecommercekotlin.model.user.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {

    @POST("auth/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @GET("products/categories")
    fun getCategories(): Call<List<Category>>

    @GET("user/me")
    fun getUserDetails(@Header("Authorization") token: String): Call<User>

    @POST("auth/refresh")
    fun refreshToken(@Body request: RefreshTokenRequest): Call<TokenResponse>

    @GET("products/category/{category}")
    fun getProductsByCategory(@Path("category") category: String): Call<ProductResponse>

    @GET("products?sortBy=rating&order=desc")
    fun getTopRatedProducts(): Call<ProductResponse>

    @GET("products/{id}")
    fun getProductsById(@Path("id") id: Int): Call<Product>

//    @GET("products/search?q={search}")
//    fun getProductsBySearching(@Path("search") search: String): Call<ProductResponse>

    @GET("products/search")
    fun getProductsBySearching(@Query("q") search: String): Call<ProductResponse>

}



