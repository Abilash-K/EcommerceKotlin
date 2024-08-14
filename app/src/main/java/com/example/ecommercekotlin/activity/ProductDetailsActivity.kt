package com.example.ecommercekotlin.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.ecommercekotlin.R
import com.example.ecommercekotlin.databinding.ActivityProductDetailsBinding
import com.example.ecommercekotlin.viewmodel.ProductDetailsViewModel

class ProductDetailsActivity : AppCompatActivity() {
    //binding
    private lateinit var binding : ActivityProductDetailsBinding
    //Viewmodel
    private lateinit var viewModel: ProductDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Get The id
        val productId = intent.getIntExtra("PRODUCT_ID",-1)

        //Back Button
        binding.productsDetailsBack.setOnClickListener {
            finish()
        }

        //ViewModel
        viewModel = ViewModelProvider(this)[ProductDetailsViewModel::class.java]

        //Fetch The Product Details
        viewModel.fetchProductsById(productId)

        //Observe
        viewModel.product.observe(this){ product ->
            binding.productName.text = product.title
            binding.productPrice.text = "$ ${product.price.toString()}"
            binding.productDescription.text = product.description
            binding.productBrand.text = product.brand
        }

        //Error
        viewModel.error.observe(this) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }


    }
}