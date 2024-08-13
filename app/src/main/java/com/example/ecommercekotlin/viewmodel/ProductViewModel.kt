package com.example.ecommercekotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ecommercekotlin.model.product.Product
import com.example.ecommercekotlin.model.product.ProductResponse
import com.example.ecommercekotlin.server.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductViewModel : ViewModel() {

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    private val _topRatedProducts = MutableLiveData<List<Product>>()
    val topRatedProducts: LiveData<List<Product>> = _topRatedProducts

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    // Existing fetchProducts function
    fun fetchProducts(category: String) {
        ApiClient.apiService.getProductsByCategory(category).enqueue(object :
            Callback<ProductResponse> {
            override fun onResponse(call: Call<ProductResponse>, response: Response<ProductResponse>) {
                if (response.isSuccessful) {
                    _products.postValue(response.body()?.products ?: emptyList())
                } else {
                    _error.postValue("Failed to load products")
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                _error.postValue(t.message ?: "Unknown error")
            }
        })
    }

    // New function to fetch top-rated products
    fun fetchTopRatedProducts() {
        ApiClient.apiService.getTopRatedProducts().enqueue(object : Callback<ProductResponse> {
            override fun onResponse(call: Call<ProductResponse>, response: Response<ProductResponse>) {
                if (response.isSuccessful) {
                    _topRatedProducts.postValue(response.body()?.products ?: emptyList())
                } else {
                    _error.postValue("Failed to load top-rated products")
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                _error.postValue(t.message ?: "Unknown error")
            }
        })
    }
}
