package com.example.ecommercekotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.ecommercekotlin.roomdb.CartItem
import com.example.ecommercekotlin.roomdb.CartRepository
import kotlinx.coroutines.launch

class CartViewModel(private val repository: CartRepository) : ViewModel() {
    // LiveData to hold a specific cart item by ID
    private val _cartItem = MutableLiveData<CartItem?>()
    val cartItem: LiveData<CartItem?>
        get() = _cartItem

    // Fetch all cart items
    val allCartItems: LiveData<List<CartItem>> = repository.getAllCartItems()

    // Insert a new cart item
    fun insert(cartItem: CartItem) = viewModelScope.launch {
        repository.insert(cartItem)
    }

    // Update an existing cart item
    fun update(cartItem: CartItem) = viewModelScope.launch {
        repository.update(cartItem)
    }

    // Get a specific cart item by ID
    fun getCartItemById(id: Int) = viewModelScope.launch {
        _cartItem.value = repository.getCartItemById(id)
    }



    // Delete a specific cart item
    fun delete(cartItem: CartItem) = viewModelScope.launch {
        repository.delete(cartItem)
    }

    // Delete all cart items
    fun deleteAllCartItems() = viewModelScope.launch {
        repository.deleteAllCartItems()
    }

    //Update
    fun updateCartItem(cartItem: CartItem) = viewModelScope.launch {
        repository.update(cartItem)
    }

}


class CartViewModelFactory(private val repository: CartRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CartViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return CartViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}