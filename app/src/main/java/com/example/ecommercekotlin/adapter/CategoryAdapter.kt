package com.example.ecommercekotlin.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommercekotlin.R
import com.example.ecommercekotlin.activity.ProductListingActivity
import com.example.ecommercekotlin.model.category.Category

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>(){

    private var categories: List<Category> = listOf()

    fun submitList(list: List<Category>){
        categories = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val categoryName: TextView = itemView.findViewById(R.id.categoryName)
        private val categoryCardItem : CardView = itemView.findViewById(R.id.categoryItemCard)

        fun bind(category: Category) {
            categoryName.text = category.name
            itemView.setOnClickListener {
                categoryCardItem.startAnimation(AnimationUtils.loadAnimation(itemView.context,R.anim.bounce))
                val context = it.context
                val intent = Intent(context, ProductListingActivity::class.java)
                intent.putExtra("CATEGORY_NAME", category.slug)
                context.startActivity(intent)
            }
        }
    }
}