package com.example.ecommercekotlin.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommercekotlin.adapter.ProductAdapter
import com.example.ecommercekotlin.databinding.ActivityProductListingBinding
import com.example.ecommercekotlin.viewmodel.ProductListingViewModel

class ProductListingActivity : AppCompatActivity() {
    //binding
    private lateinit var binding : ActivityProductListingBinding
    //viewModel
    private lateinit var viewModel: ProductListingViewModel
    //Adapter
    private lateinit var productAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Set Header
        val category = intent.getStringExtra("CATEGORY_NAME") ?: "All Products"
        val searchText = intent.getStringExtra("SEARCH_STRING")



        if (searchText != null){
            binding.productHeading.text = "Search Result for  \"$searchText\""
        }else{
            binding.productHeading.text=category.replaceFirstChar {  it.uppercase() }
        }

        //Back Button
        binding.categoryBack.setOnClickListener {
            finish()
        }

        if(category == "All Products"){
            binding.productListingRecycle.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                    if (!viewModel.isLoading.value!! && (visibleItemCount + firstVisibleItemPosition >= totalItemCount)
                        && firstVisibleItemPosition >= 0) {
                        viewModel.fetchAllProducts()
                    }
                }
            })
        }

        //Adapter
        productAdapter = ProductAdapter(emptyList())
        binding.productListingRecycle.layoutManager = GridLayoutManager(this,2)
        binding.productListingRecycle.adapter = productAdapter

        //ViewModel
        viewModel = ViewModelProvider(this)[ProductListingViewModel::class.java]

        //Observe
        viewModel.products.observe(this) { products ->
            //If products is empty just display the emptyPage
            if (products.isEmpty()){
                binding.emptyImage.visibility = View.VISIBLE
                binding.productListingRecycle.visibility = View.GONE
            }else{
                binding.emptyImage.visibility = View.GONE
                binding.productListingRecycle.visibility = View.VISIBLE
                productAdapter.updateProducts(products)
            }

        }

        //Loading
        viewModel.isLoading.observe(this) { isLoading ->
            binding.productListingLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        //Error
        viewModel.error.observe(this) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }

        //FetchProducts check if search text is there or not and category is ALl Products

        if(searchText != null){
         //fetch the search Text
            viewModel.fetchProductsBySearch(searchText)
        }else if (category == "All Products") {
           //fetch All Products
            viewModel.fetchAllProducts()
        }else{
            viewModel.fetchProductsByCategory(category)
        }
    }


}
