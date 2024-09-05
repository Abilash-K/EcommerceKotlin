package com.example.ecommercekotlin.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.ecommercekotlin.R
import com.example.ecommercekotlin.utility.TokenManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics

class HomeActivity : AppCompatActivity() {
    //nacController
    private lateinit var navController: NavController
    private lateinit var analytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        // Obtain the FirebaseAnalytics instance.
        analytics = Firebase.analytics




        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        navController=navHostFragment.navController
        val bottomNav = findViewById<BottomNavigationView>(R.id.navigationView)

        setupWithNavController(bottomNav,navController)


        //getToken
        val token = TokenManager.getToken(this)
        Log.d("Token", "Token: $token")
        if (token == null){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }


}