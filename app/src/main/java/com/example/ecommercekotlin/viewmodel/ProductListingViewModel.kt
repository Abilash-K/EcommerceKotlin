package com.example.ecommercekotlin.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ecommercekotlin.model.product.Product
import com.example.ecommercekotlin.model.product.ProductResponse
import com.example.ecommercekotlin.server.ApiClient
import com.example.ecommercekotlin.server.ApiServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductListingViewModel : ViewModel() {

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    fun fetchProductsByCategory(category: String) {
        _isLoading.value = true
        ApiClient.apiService.getProductsByCategory(category).enqueue(object : Callback<ProductResponse> {
            override fun onResponse(call: Call<ProductResponse>, response: Response<ProductResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _products.value = response.body()?.products ?: emptyList()
                } else {
                    _error.value = "Failed to load products."
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                _isLoading.value = false
                _error.value = t.message
            }
        })
    }
}