package com.example.ecommercekotlin.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "cart_items")
data class CartItem(
    @PrimaryKey
    val productId: Int,
    val productName: String,
    val productBrand: String?,
    val productPrice: Double,
    val productImage: String,
    var quantity: Int
)