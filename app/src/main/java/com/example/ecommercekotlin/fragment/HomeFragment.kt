package com.example.ecommercekotlin.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.ecommercekotlin.activity.ProductDetailsActivity
import com.example.ecommercekotlin.activity.SearchActivity
import com.example.ecommercekotlin.adapter.ProductAdapter
import com.example.ecommercekotlin.databinding.FragmentHomeBinding
import com.example.ecommercekotlin.viewmodel.ProductViewModel
import com.example.ecommercekotlin.viewmodel.ProfileViewModel
import com.example.ecommercekotlin.viewmodel.RandomNumberViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val profileViewModel: ProfileViewModel by viewModels()
    private val productViewModel: ProductViewModel by viewModels()
    private lateinit var productAdapter: ProductAdapter
    private lateinit var topProducts : ProductAdapter

    //Timer For Flash Sale ViewModel
    private val randomNumberViewModel : RandomNumberViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment using view binding
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
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

        //Flash Sale CardView
        randomNumberViewModel.timeLeft.observe(viewLifecycleOwner) { timeLeft ->
            binding.timeLeft.text = "Time Left : $timeLeft"
        }
        // Observe the randomNumber LiveData

        // Set the random number in the TextView
        binding.cardView.setOnClickListener {
            val randomNumber = randomNumberViewModel.randomNumber.value
                //Pass The RandomNumber to Product Details Page
                val intent = Intent(requireContext(), ProductDetailsActivity::class.java)
                intent.putExtra("PRODUCT_ID", randomNumber)
                startActivity(intent)
            Toast.makeText(requireContext(), "Random Number: $randomNumber", Toast.LENGTH_SHORT).show()
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

        //SearchTransition
        binding.searchInput.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                // Request focus and show the keyboard
                binding.searchInput.requestFocus()

                // Transition to SearchActivity immediately
                val intent = Intent(requireContext(), SearchActivity::class.java)
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    requireActivity(),
                    binding.searchInput, // The shared view
                    "searchTransition" // The transition name
                )
                startActivity(intent, options.toBundle())

                true
            } else {
                false
            }
        }

    }


    override fun onPause() {
        super.onPause()
        randomNumberViewModel.saveStartTime()
    }



}