package com.example.ecommercekotlin.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommercekotlin.R
import com.example.ecommercekotlin.activity.ProductDetailsActivity
import com.example.ecommercekotlin.roomdb.CartItem

class CartAdapter(private var cartItem: List<CartItem>,private val onCartItemUpdated: (CartItem) -> Unit,
                  private val onCartItemDeleted: (CartItem) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    class CartViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val productImg : ImageView = itemView.findViewById(R.id.productImg)
        val productBrand : TextView = itemView.findViewById(R.id.productBrand)
        val productName : TextView = itemView.findViewById(R.id.productName)
        val productPrice : TextView = itemView.findViewById(R.id.productPrice)
        val deleteBtn : ImageButton = itemView.findViewById(R.id.deleteBtn)
        val plusBtn : ImageButton = itemView.findViewById(R.id.plusBtn)
        val minusBtn : ImageButton = itemView.findViewById(R.id.minusBtn)
        val quantityValue : TextView = itemView.findViewById(R.id.quantityValue)
        val cartItem : CardView = itemView.findViewById(R.id.cartItem)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_item,parent,false)
        return CartViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cartItem.size
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartItem = cartItem[position]
        holder.productBrand.text = cartItem.productBrand
        holder.productName.text = cartItem.productName
        holder.productPrice.text = "$ ${cartItem.productPrice}"
        holder.quantityValue.text = cartItem.quantity.toString()

        // Plus Button Logic
        holder.plusBtn.setOnClickListener {
            cartItem.quantity++
            holder.quantityValue.text = cartItem.quantity.toString()
            onCartItemUpdated(cartItem)
        }

        // Minus Button Logic
        holder.minusBtn.setOnClickListener {
            if (cartItem.quantity > 1) {
                cartItem.quantity--
                holder.quantityValue.text = cartItem.quantity.toString()
                onCartItemUpdated(cartItem)
            }
        }

        // Delete Button Logic
        holder.deleteBtn.setOnClickListener {
            onCartItemDeleted(cartItem)
        }

        //Glide Image
        Glide.with(holder.itemView.context)
            .load(cartItem.productImage)
            .into(holder.productImg)

        //Move To ProductDetails Page on Clicking the card
        holder.cartItem.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, ProductDetailsActivity::class.java)
            intent.putExtra("PRODUCT_ID", cartItem.productId)
            context.startActivity(intent)
        }




    }
    fun updateCartItems(newCartItems: List<CartItem>) {
        cartItem = newCartItems
        notifyDataSetChanged()
    }

}