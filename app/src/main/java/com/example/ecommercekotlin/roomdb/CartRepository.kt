package com.example.ecommercekotlin.roomdb

import androidx.lifecycle.LiveData


class CartRepository(private val cartDao: CartDao) {

    // Insert a new cart item
    suspend fun insert(cartItem: CartItem) {
        cartDao.insert(cartItem)
    }

    // Update an existing cart item
    suspend fun update(cartItem: CartItem) {
        cartDao.update(cartItem)
    }

    // Get all cart items
     fun getAllCartItems(): LiveData<List<CartItem>> {
        return cartDao.getAllCartItems()
    }

    // Get a cart item by its product ID
     suspend fun getCartItemById(id: Int): CartItem? {
        return cartDao.getCartItemById(id)
    }

    // Delete a specific cart item
    suspend fun delete(cartItem: CartItem) {
        cartDao.delete(cartItem)
    }

    // Delete all cart items
    suspend fun deleteAllCartItems() {
        cartDao.deleteAllCartItems()
    }
}