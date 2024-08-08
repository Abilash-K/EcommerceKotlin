package com.example.ecommercekotlin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommercekotlin.R
import com.example.ecommercekotlin.fragment.OnboardingItem

class OnboardingPagerAdapter(private val items: List<OnboardingItem>) : RecyclerView.Adapter<OnboardingPagerAdapter.OnboadringViewHolder>() {
    inner class OnboadringViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        private val imageView : ImageView = view.findViewById(R.id.onboarding_image_1)
        private val titleView : TextView = view.findViewById(R.id.onboarding_heading_1)
        private val descriptionView : TextView = view.findViewById(R.id.onboarding_body_1)

        fun bind(item : OnboardingItem) {
            imageView.setImageResource(item.imageResId)
            titleView.text = item.title
            descriptionView.text = item.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboadringViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.onboarding_layout, parent, false)
        return OnboadringViewHolder(view)
    }

    override fun getItemCount()= items.size

    override fun onBindViewHolder(holder: OnboadringViewHolder, position: Int) {
        holder.bind(items[position])
    }
}