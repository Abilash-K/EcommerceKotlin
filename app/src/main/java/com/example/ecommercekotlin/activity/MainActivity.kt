package com.example.ecommercekotlin.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle

import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.ecommercekotlin.R
import com.example.ecommercekotlin.fragment.OnboardingFragment

class MainActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        
        sharedPreferences = getSharedPreferences("app_prefs",Context.MODE_PRIVATE)
        val isFirstTime = sharedPreferences.getBoolean("isFirstTime",true)

        if (isFirstTime){
            supportFragmentManager.beginTransaction()
                .replace(R.id.main, OnboardingFragment())
                .commit()
        }else{
            showLoginScreen()
        }

    }

    private fun showLoginScreen() {
        //Load Login Activity
        val intent = Intent(this,LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun completeOnboarding() {
       sharedPreferences.edit().putBoolean("isFirstTime",false).apply()
        showLoginScreen()
    }
}