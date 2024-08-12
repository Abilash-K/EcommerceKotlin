package com.example.ecommercekotlin.model.user

data class User(
    val firstName: String,
    val lastName: String,
    val address: Address,
    val image : String
) {
    data class Address(
        val address: String,
        val city: String,
        val state: String,
        val postalCode: String,
        val country: String
    )
}