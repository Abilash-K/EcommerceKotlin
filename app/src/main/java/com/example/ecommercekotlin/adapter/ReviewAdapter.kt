package com.example.ecommercekotlin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommercekotlin.R
import com.example.ecommercekotlin.model.product.Review

class ReviewAdapter(private var reviews : List<Review>) : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>(){
    class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val reviewName : TextView = itemView.findViewById(R.id.ratingName)
        val reviewDate : TextView = itemView.findViewById(R.id.ratingDate)
        val reviewReview : TextView = itemView.findViewById(R.id.ratingReview)
        val reviewValue : TextView = itemView.findViewById(R.id.ratingValue)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.review_item,parent,false)
        return ReviewViewHolder(view)
    }

    override fun getItemCount(): Int {
        return reviews.size
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
      val review = reviews[position]
        holder.reviewName.text = review.reviewerName
        holder.reviewDate.text = review.date
        holder.reviewReview.text = review.comment
        holder.reviewValue.text = review.rating.toString()

    }
}