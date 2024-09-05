package com.example.ecommercekotlin.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommercekotlin.R
import com.example.ecommercekotlin.adapter.CategoryAdapter
import com.example.ecommercekotlin.databinding.FragmentCategoryBinding
import com.example.ecommercekotlin.viewmodel.CategoryViewModel

class CategoryFragment : Fragment() {

    private val categoryViewModel: CategoryViewModel by viewModels()
    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Back to Home Fragment
        binding.categoryBack.setOnClickListener {
            //Go to the Home Fragment using the nav graph
            val navController = findNavController()
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.nav_category, true)
                .build()
            navController.navigate(R.id.action_nav_category_to_nav_home, null, navOptions)
        }
        val adapter = CategoryAdapter()
        binding.categoryRecycle.layoutManager = LinearLayoutManager(context)
        binding.categoryRecycle.adapter = adapter

        categoryViewModel.categories.observe(viewLifecycleOwner) { categories ->
            adapter.submitList(categories)
        }

        categoryViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.categoryLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}