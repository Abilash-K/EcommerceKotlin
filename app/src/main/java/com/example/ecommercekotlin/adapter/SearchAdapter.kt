package com.example.ecommercekotlin.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommercekotlin.R
import com.example.ecommercekotlin.activity.ProductListingActivity
import com.example.ecommercekotlin.activity.SearchActivity

class SearchAdapter(private var searchList : List<String>) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>(){
    class SearchViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val searchText : TextView = itemView.findViewById(R.id.searchItem)
        val searchCardView : CardView = itemView.findViewById(R.id.searchCardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_item,parent,false)
        return SearchViewHolder(view)
    }

    override fun getItemCount(): Int {
        return searchList.size
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val searchItem = searchList[position]
        holder.searchText.text = searchItem

        //card onClick
        holder.searchCardView.setOnClickListener {
            //Pass the text to the ProductListingActivity and finish the SearchActivity
            val intent = Intent(holder.itemView.context, ProductListingActivity::class.java)
            intent.putExtra("SEARCH_STRING", searchItem)
            holder.itemView.context.startActivity(intent)
            //Finish the Search Activity
            (holder.itemView.context as? SearchActivity)?.finish()

        }

    }

}