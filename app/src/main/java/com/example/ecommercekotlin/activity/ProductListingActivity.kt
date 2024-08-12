package com.example.ecommercekotlin.activity

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.ecommercekotlin.R

class ProductListingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_listing)

        val category = intent.getStringExtra("CATEGORY_NAME").toString().replaceFirstChar {  it.uppercase() } ?: "All Products"
        val heading = findViewById<TextView>(R.id.productHeading)
        heading.text = category
    }


}
