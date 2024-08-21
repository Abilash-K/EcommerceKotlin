package com.example.ecommercekotlin.activity

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ecommercekotlin.adapter.SearchAdapter
import com.example.ecommercekotlin.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {
    //binding
    private lateinit var binding : ActivitySearchBinding
    //Adapter
    private lateinit var adapter : SearchAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //DummyData for List
        val dummyList = listOf("Apple", "Iphone", "Asus", "Samsung", "Table", "Chair")
        //SetAdapter
        adapter = SearchAdapter(dummyList)


        //SetLayoutManager
        binding.featuredSearchRecycler.layoutManager = GridLayoutManager(this,3)
        binding.featuredSearchRecycler.adapter = adapter

        //Send the SearchText when
        binding.searchInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                //navigate to Product Listing
                val intent = Intent(this, ProductListingActivity::class.java)
                intent.putExtra("SEARCH_STRING", binding.searchInput.text.toString())
                startActivity(intent)
                return@setOnEditorActionListener true
            }
            false
        }


    }

}