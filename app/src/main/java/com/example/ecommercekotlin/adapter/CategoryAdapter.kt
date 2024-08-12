package com.example.ecommercekotlin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommercekotlin.R
import com.example.ecommercekotlin.model.category.Category

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>(){

    private var categories : List<Category> = listOf()

    fun submitList(list : List<Category>){
        categories = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryAdapter.CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryAdapter.CategoryViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    class CategoryViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        private val categoryName : TextView = itemView.findViewById(R.id.categoryName)

        fun bind(category: Category){
            categoryName.text = category.name
        }
    }
}
