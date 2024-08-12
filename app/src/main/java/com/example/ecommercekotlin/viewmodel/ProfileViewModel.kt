package com.example.ecommercekotlin.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ecommercekotlin.model.user.User
import com.example.ecommercekotlin.server.ApiClient
import com.example.ecommercekotlin.utility.TokenManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel : ViewModel() {
    // LiveData to hold the user's profile data
    private val _userDetails = MutableLiveData<User>()
    val userDetails: LiveData<User> get() = _userDetails

    //Error
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    //Function Fetch UserDetails
    fun fetchUserDetails(context: Context) {
        val token = TokenManager.getToken(context)

        if (token != null) {
            ApiClient.apiService.getUserDetails("Bearer $token").enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        _userDetails.postValue(response.body())
                    } else {
                        _error.postValue("Failed to fetch user details: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    _error.postValue("Error: ${t.message}")
                }
            })
        } else {
            _error.postValue("Token not found")
        }
    }

}