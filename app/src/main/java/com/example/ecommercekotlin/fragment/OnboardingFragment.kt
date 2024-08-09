package com.example.ecommercekotlin.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.ecommercekotlin.R
import com.example.ecommercekotlin.activity.MainActivity
import com.example.ecommercekotlin.adapter.OnboardingPagerAdapter

class OnboardingFragment : Fragment() {

    private lateinit var viewPager : ViewPager2
    private lateinit var nextButton : Button
    private lateinit var toLogin : TextView

    private val onboardingItem = listOf(
        OnboardingItem(R.drawable.onboarding_image_1, "Discover Our Products", "Browse thousands of products, from fashion to tech. Find what you love, effortlessly."),
        OnboardingItem(R.drawable.onboarding_image_2,"Easy and Secure Payments", "Enjoy seamless payments with our secure payment options."),
        OnboardingItem(R.drawable.onboarding_image_2,"Fast Delivery", "Get your products delivered to your doorstep in no time.")
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_onboarding, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager = view.findViewById(R.id.viewPager)
        nextButton = view.findViewById(R.id.onboardingBtn)
        toLogin = view.findViewById(R.id.onboarding_login)

        val adapter = OnboardingPagerAdapter(onboardingItem)
        viewPager.adapter = adapter

      viewPager.registerOnPageChangeCallback(object  : ViewPager2.OnPageChangeCallback(){
          override fun onPageSelected(position: Int) {
              super.onPageSelected(position)
              updateIndicator(position)
              nextButton.text = if (position == adapter.itemCount - 1) "Finish" else "Next"
          }
      })

        nextButton.setOnClickListener {
            val current = viewPager.currentItem
            if(current + 1 < adapter.itemCount){
                viewPager.currentItem = current + 1
            }else{
                (activity as MainActivity).completeOnboarding()
            }
        }

        toLogin.setOnClickListener{
            (activity as MainActivity).completeOnboarding()
        }

    }

    private fun updateIndicator(position: Int){
        val indicator1 = view?.findViewById<View>(R.id.indicator_1)
        val indicator2 = view?.findViewById<View>(R.id.indicator_2)
        val indicator3 = view?.findViewById<View>(R.id.indicator_3)

        indicator1?.setBackgroundResource(if (position == 0) R.drawable.indicator_active else R.drawable.indicator_inactive)
        indicator2?.setBackgroundResource(if (position == 1) R.drawable.indicator_active else R.drawable.indicator_inactive)
        indicator3?.setBackgroundResource(if (position == 2) R.drawable.indicator_active else R.drawable.indicator_inactive)

    }

}

data class OnboardingItem(val imageResId: Int, val title: String, val description: String)