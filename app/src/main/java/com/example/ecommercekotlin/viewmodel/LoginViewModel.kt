package com.example.ecommercekotlin.viewmodel

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ecommercekotlin.model.login.LoginRequest
import com.example.ecommercekotlin.model.login.LoginResponse
import com.example.ecommercekotlin.server.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {

    // LiveData to observe the login result
    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse> = _loginResponse

    //error
    private val _loginError = MutableLiveData<String>()
    val loginError: LiveData<String> get() = _loginError

    //Loading
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    // Function to handle the login process
    fun login(username: String, password: String) {
        _isLoading.value = true

        val request = LoginRequest(username, password)

        ApiClient.apiService.login(request).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful){
                    _isLoading.value = false
                    _loginResponse.value = response.body()
                }else{
                    _isLoading.value = false
                    _loginError.value = "Error: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLoading.value = false
                _loginError.value = t.message

            }

        })

    }

}