package com.example.ecommercekotlin.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommercekotlin.adapter.CartAdapter
import com.example.ecommercekotlin.databinding.FragmentCartBinding
import com.example.ecommercekotlin.roomdb.AppDatabase
import com.example.ecommercekotlin.roomdb.CartRepository
import com.example.ecommercekotlin.viewmodel.CartViewModel
import com.example.ecommercekotlin.viewmodel.CartViewModelFactory


class CartFragment : Fragment() {
    //Binding
    private lateinit var binding: FragmentCartBinding
    //ViewModel
    private lateinit var cartViewModel: CartViewModel
    //Adapter
    private lateinit var adapter: CartAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Initialize CartDao, Repository, and ViewModelFactory
        val application = requireNotNull(this.activity).application
        val cartDao = AppDatabase.getInstance(application).cartDao()
        val repository = CartRepository(cartDao)
        val viewModelFactory = CartViewModelFactory(repository)

        // Initialize CartViewModel
        cartViewModel = ViewModelProvider(this, viewModelFactory)[CartViewModel::class.java]

        // Initialize the CartAdapter with update and delete callbacks
        adapter = CartAdapter(emptyList(), { cartItem ->
            cartViewModel.updateCartItem(cartItem)
        }, { cartItem ->
            cartViewModel.delete(cartItem)
        })

        // Set up RecyclerView
        binding.cartRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.cartRecycler.adapter = adapter

        // Observe the CartViewModel
        cartViewModel.allCartItems.observe(viewLifecycleOwner) { cartItems ->
            adapter.updateCartItems(cartItems)

            //Calculate Total Amount
            val totalAmount = cartItems.sumOf { it.productPrice * it.quantity }
            binding.totalAmt.text = "$ $totalAmount"

        }



    }

}