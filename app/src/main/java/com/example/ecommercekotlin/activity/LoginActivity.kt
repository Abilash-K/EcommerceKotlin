package com.example.ecommercekotlin.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.ecommercekotlin.R
import com.example.ecommercekotlin.databinding.ActivityLoginBinding
import com.example.ecommercekotlin.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {
    //LoginViewModel
    private val loginViewModel : LoginViewModel by viewModels()

    //ViewBinding
    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Initialize binding
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            val username = binding.loginEmail.text.toString()
            val password = binding.loginPassword.text.toString()
            loginViewModel.login(username,password)
        }

        //loginViewModel
        loginViewModel.loginResponse.observe(this, Observer { response ->
            binding.loginProgress.visibility = android.view.View.GONE
            Toast.makeText(this,"Login Successful: ${response.firstName} ${response.lastName}", Toast.LENGTH_SHORT).show()
            //Log.d("LoginResponse", "Success: ${response.token}")
        })

        //LoginError
        loginViewModel.loginError.observe(this, Observer { error ->
            binding.loginProgress.visibility = android.view.View.GONE
            Toast.makeText(this,"Login Failed: $error", Toast.LENGTH_SHORT).show()
            Log.d("LoginError", "Error: $error")
        })

        loginViewModel.isLoading.observe(this, Observer { isLoading ->
            binding.loginProgress.visibility = if (isLoading) android.view.View.VISIBLE else android.view.View.GONE
        })

    }
}