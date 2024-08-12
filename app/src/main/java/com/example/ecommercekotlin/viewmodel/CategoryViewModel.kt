package com.example.ecommercekotlin.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ecommercekotlin.model.category.Category
import com.example.ecommercekotlin.server.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class CategoryViewModel : ViewModel() {
    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> get() = _categories

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    init {
        fetchCategories()
    }

    private fun fetchCategories() {
        _isLoading.value = true

        ApiClient.apiService.getCategories().enqueue(object : Callback<List<Category>> {
            override fun onResponse(call: Call<List<Category>>, response: Response<List<Category>>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _categories.value = response.body()
                } else {
                    Log.e("CategoryViewModel", "Error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                _isLoading.value = false
                Log.e("CategoryViewModel", "Failure: ${t.message}")
            }
        })
    }
}