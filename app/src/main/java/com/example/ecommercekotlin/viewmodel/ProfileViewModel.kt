package com.example.ecommercekotlin.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ecommercekotlin.model.user.TokenResponse
import com.example.ecommercekotlin.model.user.User
import com.example.ecommercekotlin.server.ApiClient
import com.example.ecommercekotlin.utility.TokenManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
//
//class ProfileViewModel : ViewModel() {
//    // LiveData to hold the user's profile data
//    private val _userDetails = MutableLiveData<User>()
//    val userDetails: LiveData<User> get() = _userDetails
//
//    //Error
//    private val _error = MutableLiveData<String>()
//    val error: LiveData<String> get() = _error
//
//    //Function Fetch UserDetails
//    fun fetchUserDetails(context: Context) {
//        val token = TokenManager.getToken(context)
//
//        if (token != null) {
//            ApiClient.apiService.getUserDetails("Bearer $token").enqueue(object : Callback<User> {
//                override fun onResponse(call: Call<User>, response: Response<User>) {
//                    if (response.isSuccessful) {
//                        _userDetails.postValue(response.body())
//                    } else {
//                        _error.postValue("Failed to fetch user details: ${response.message()}")
//                    }
//                }
//
//                override fun onFailure(call: Call<User>, t: Throwable) {
//                    _error.postValue("Error: ${t.message}")
//                }
//            })
//        } else {
//            _error.postValue("Token not found")
//        }
//    }
//
//}


class ProfileViewModel : ViewModel() {
    // LiveData to hold the user's profile data
    private val _userDetails = MutableLiveData<User>()
    val userDetails: LiveData<User> get() = _userDetails

    // Error LiveData
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    // Function to fetch user details
    fun fetchUserDetails(context: Context) {
        val token = TokenManager.getToken(context)

        if (token != null) {
            ApiClient.apiService.getUserDetails("Bearer $token").enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        _userDetails.postValue(response.body())
                    } else if (response.code() == 401) {
                        // Unauthorized - Token might have expired
                        refreshToken(context)
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

    // Function to refresh the token
    private fun refreshToken(context: Context) {
        val refreshToken = TokenManager.getRefreshToken(context)

        if (refreshToken != null) {
            // Make a request to refresh the token
            val requestBody = mapOf(
                "refreshToken" to refreshToken,
                "expiresInMins" to 60
            )

            ApiClient.apiService.refreshToken(requestBody).enqueue(object : Callback<TokenResponse> {
                override fun onResponse(call: Call<TokenResponse>, response: Response<TokenResponse>) {
                    if (response.isSuccessful) {
                        // Save the new token
                        response.body()?.let { tokenResponse ->
                            TokenManager.saveTokens(
                                context,
                                tokenResponse.token,
                                tokenResponse.refreshToken
                            )
                        }
                        // Retry fetching the user details with the new token
                        fetchUserDetails(context)
                    } else {
                        _error.postValue("Failed to refresh token: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                    _error.postValue("Error refreshing token: ${t.message}")
                }
            })
        } else {
            _error.postValue("Refresh token not found")
        }
    }
}