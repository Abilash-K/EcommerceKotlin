package com.example.ecommercekotlin.activity

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.ecommercekotlin.R
import com.example.ecommercekotlin.adapter.ImageSliderAdapter
import com.example.ecommercekotlin.databinding.ActivityProductDetailsBinding
import com.example.ecommercekotlin.viewmodel.ProductDetailsViewModel

class ProductDetailsActivity : AppCompatActivity() {
    //binding
    private lateinit var binding : ActivityProductDetailsBinding
    //Viewmodel
    private lateinit var viewModel: ProductDetailsViewModel
    // List to hold indicators
    private val indicators = ArrayList<View>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Get The id
        val productId = intent.getIntExtra("PRODUCT_ID", -1)

        //Back Button
        binding.productsDetailsBack.setOnClickListener {
            finish()
        }

        //ViewModel
        viewModel = ViewModelProvider(this)[ProductDetailsViewModel::class.java]

        //Fetch The Product Details
        viewModel.fetchProductsById(productId)

        //Observe
        viewModel.product.observe(this) { product ->
            binding.productName.text = product.title
            binding.productPrice.text = "$ ${product.price.toString()}"
            binding.productDescription.text = product.description
            binding.productBrand.text = product.brand

            //Adapter for Images
            val adapter = ImageSliderAdapter(product.images)
            binding.productCarasoul.adapter = adapter
            //Indicator
            // Dynamically create indicators based on the number of images
            binding.indicatorContainer.removeAllViews() // Clear any existing indicators
            indicators.clear() // Clear the indicators list

// Define the size for the indicators (width and height)
            val indicatorSize = resources.getDimensionPixelSize(R.dimen.indicator_size) // Set this dimension in your dimens.xml

// Create indicators based on the number of images
            for (i in product.images.indices) {
                val indicator = View(this)
                indicator.setBackgroundResource(if (i == 0) R.drawable.indicator_active else R.drawable.indicator_inactive)

                // Set layout parameters for the indicator size and spacing between indicators
                val params = LinearLayout.LayoutParams(indicatorSize, indicatorSize)
                params.setMargins(8, 0, 8, 0) // Adjust the spacing as needed
                indicator.layoutParams = params

                // Add indicator to the container and list
                binding.indicatorContainer.addView(indicator)
                indicators.add(indicator)

            }
                // Update the indicator on page change
                binding.productCarasoul.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        updateIndicator(position) // Call updateIndicator with the current position
                    }
                })






            //Error
            viewModel.error.observe(this) { errorMessage ->
                errorMessage?.let {
                    Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                }
            }


        }


    }

    // Function to update indicators based on the current position
    private fun updateIndicator(position: Int) {
        for (i in indicators.indices) {
            indicators[i].setBackgroundResource(
                if (i == position) R.drawable.indicator_active else R.drawable.indicator_inactive
            )
        }
    }


}