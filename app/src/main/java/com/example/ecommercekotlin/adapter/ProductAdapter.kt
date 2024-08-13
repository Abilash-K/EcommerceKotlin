package com.example.ecommercekotlin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommercekotlin.R
import com.example.ecommercekotlin.model.product.Product


class ProductAdapter(private var products: List<Product>) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImg: ImageView = itemView.findViewById(R.id.productImg)
        val productBrand: TextView = itemView.findViewById(R.id.productBrand)
        val productName: TextView = itemView.findViewById(R.id.productName)
        val productPrice: TextView = itemView.findViewById(R.id.productPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_card_item, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.productBrand.text = product.brand
        holder.productName.text = product.title
        holder.productPrice.text = "$ ${product.price}"

        Glide.with(holder.itemView.context)
            .load(product.thumbnail)
            .into(holder.productImg)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    // New method to update products dynamically
    fun updateProducts(newProducts: List<Product>) {
        this.products = newProducts
        notifyDataSetChanged()
    }
}