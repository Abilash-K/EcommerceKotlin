package com.example.ecommercekotlin.roomdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cartItem: CartItem)

    @Update
    suspend fun update(cartItem: CartItem)

    @Query("SELECT * FROM cart_items")
    fun getAllCartItems() : LiveData<List<CartItem>>

    @Query("SELECT * FROM cart_items WHERE productId = :id LIMIT 1")
    suspend fun getCartItemById(id: Int): CartItem?

    @Delete
    suspend fun delete(cartItem: CartItem)

    @Query("DELETE FROM cart_items")
    suspend fun deleteAllCartItems()

}