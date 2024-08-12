package com.example.ecommercekotlin.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommercekotlin.R
import com.example.ecommercekotlin.adapter.CategoryAdapter
import com.example.ecommercekotlin.viewmodel.CategoryViewModel


class CategoryFragment : Fragment() {

    private val categoryViewModel : CategoryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.categoryRecycle)

        val adapter = CategoryAdapter()
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        categoryViewModel.categories.observe(viewLifecycleOwner, Observer { categories ->
            categories?.let {
                adapter.submitList(it)
            }
        })

        categoryViewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            val progressBar = view.findViewById<ProgressBar>(R.id.categoryLoading)
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE

        })


    }
}