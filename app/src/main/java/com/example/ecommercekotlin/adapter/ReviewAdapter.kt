package com.example.ecommercekotlin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommercekotlin.R
import com.example.ecommercekotlin.model.product.Review
import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.util.Date
import java.util.Locale
import java.util.TimeZone

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
        // Parse the ISO 8601 date-time string
        val isoFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        isoFormat.timeZone = TimeZone.getTimeZone("UTC")
        val date: Date? = isoFormat.parse(review.date)
        // Format
        val outputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val formattedDateTime = date?.let { outputFormat.format(it) }

        holder.reviewName.text = review.reviewerName
        holder.reviewDate.text = formattedDateTime
        holder.reviewReview.text = review.comment
        holder.reviewValue.text = review.rating.toString()

    }
}