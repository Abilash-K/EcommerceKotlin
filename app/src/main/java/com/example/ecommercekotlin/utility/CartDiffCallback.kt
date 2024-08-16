package com.example.ecommercekotlin.utility

import androidx.recyclerview.widget.DiffUtil
import com.example.ecommercekotlin.roomdb.CartItem

class CartDiffCallback : DiffUtil.ItemCallback<CartItem>() {
    override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
        return oldItem == newItem
    }
}