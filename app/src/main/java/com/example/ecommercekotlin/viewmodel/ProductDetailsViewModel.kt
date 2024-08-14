package com.example.ecommercekotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ecommercekotlin.model.product.Product
import com.example.ecommercekotlin.server.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductDetailsViewModel : ViewModel() {

    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> = _product

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    fun fetchProductsById(id : Int){
        _isLoading.value = true
        ApiClient.apiService.getProductsById(id).enqueue(object : Callback<Product>{
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                _isLoading.value=false
                if (response.isSuccessful){
                    _product.postValue(response.body())
                }else{
                    _error.postValue("Failed to load product details")
                }
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                _error.postValue(t.message ?: "Unknown error")
            }

        })
    }
}