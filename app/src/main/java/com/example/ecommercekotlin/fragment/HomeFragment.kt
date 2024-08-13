package com.example.ecommercekotlin.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.ecommercekotlin.R
import com.example.ecommercekotlin.adapter.ProductAdapter
import com.example.ecommercekotlin.databinding.FragmentHomeBinding
import com.example.ecommercekotlin.viewmodel.ProductViewModel
import com.example.ecommercekotlin.viewmodel.ProfileViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val profileViewModel: ProfileViewModel by viewModels()
    private val productViewModel: ProductViewModel by viewModels()
    private lateinit var productAdapter: ProductAdapter
    private lateinit var topProducts : ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment using view binding
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Fetch user details
        profileViewModel.fetchUserDetails(requireContext())

        // Observe the user details LiveData
        profileViewModel.userDetails.observe(viewLifecycleOwner) { user ->
            // Set the user's first name in the TextView
            binding.homeWelcome.text = "Welcome, ${user.firstName}"

            // Set the user's city and state code in the location TextView
            binding.homeLocation.text = "${user.address.city}, ${user.address.state}"

            // Load the user's profile image using Glide
            Glide.with(this)
                .load(user.image)
                .into(binding.homeProfile)
        }

        // Observe the error LiveData
        profileViewModel.error.observe(viewLifecycleOwner) { error ->
            // Handle the error (e.g., show a Toast)
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }

        //setup Recycler View
        productAdapter = ProductAdapter(emptyList())
        binding.homeRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = productAdapter
        }


        //fetch products
        productViewModel.fetchProducts("laptops")
        productViewModel.products.observe(viewLifecycleOwner) { products ->
            productAdapter = ProductAdapter(products)
            binding.homeRecyclerView.adapter = productAdapter
        }

        productViewModel.error.observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }


        //Top Products
        topProducts = ProductAdapter(emptyList())
        binding.topRecyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = topProducts
            isNestedScrollingEnabled = false
        }
        //Fetch Top Products
        productViewModel.fetchTopRatedProducts()

        productViewModel.topRatedProducts.observe(viewLifecycleOwner) { products ->
            topProducts.updateProducts(products)

        }





    }
}