package com.example.ecommercekotlin.roomdb

import androidx.lifecycle.LiveData

import android.content.Context


class CartRepository(context: Context) {

    private val cartDao: CartDao = AppDatabase.getInstance(context).cartDao()

    suspend fun insertCartItem(cartItem: CartItem) {
        cartDao.insert(cartItem)
    }

    suspend fun updateCartItem(cartItem: CartItem) {
        cartDao.update(cartItem)
    }

    suspend fun deleteCartItem(cartItem: CartItem) {
        cartDao.delete(cartItem)
    }

    suspend fun getAllCartItems(): List<CartItem> {
        return cartDao.getAllCartItems()
    }

    fun getCartItemsLiveData(): LiveData<List<CartItem>> {
        return cartDao.getCartItemsLiveData()
    }
}