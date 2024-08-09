package com.example.ecommercekotlin.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.ecommercekotlin.databinding.ActivityLoginBinding
import com.example.ecommercekotlin.utility.TokenManager
import com.example.ecommercekotlin.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {
    //LoginViewModel
    private val loginViewModel : LoginViewModel by viewModels()

    //ViewBinding
    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Check for Token
        val token = TokenManager.getToken(this)
        if (token != null){
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        //Initialize binding
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            val username = binding.loginUsername.text.toString()
            val password = binding.loginPassword.text.toString()
           //validation
            if (username.isEmpty()){
                //seterror
                binding.loginUsername.error = "Please enter username"
                binding.loginUsername.requestFocus()
                return@setOnClickListener
            }else if (password.isEmpty()){
                //seterror
                binding.loginPassword.error = "Please enter password"
                binding.loginPassword.requestFocus()
                return@setOnClickListener
            }else{
                binding.loginUsername.error = null
                binding.loginPassword.error = null
                loginViewModel.login(username,password)
            }

        }

        //loginViewModel
        loginViewModel.loginResponse.observe(this) { response ->
            binding.loginProgress.visibility = android.view.View.GONE
            Toast.makeText(
                this,
                "Login Successful: ${response.firstName} ${response.lastName}",
                Toast.LENGTH_SHORT
            ).show()
            //save token
            TokenManager.saveTokens(this,response.token,response.refreshToken)

            //navigate
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()

            //Log.d("LoginResponse", "Success: ${response.token}")
        }

        //LoginError
        loginViewModel.loginError.observe(this) { error ->
            binding.loginProgress.visibility = android.view.View.GONE
            Toast.makeText(this, "Login Failed: $error", Toast.LENGTH_SHORT).show()
            Log.d("LoginError", "Error: $error")
        }

        loginViewModel.isLoading.observe(this) { isLoading ->
            binding.loginProgress.visibility =
                if (isLoading) android.view.View.VISIBLE else android.view.View.GONE
        }

    }


}