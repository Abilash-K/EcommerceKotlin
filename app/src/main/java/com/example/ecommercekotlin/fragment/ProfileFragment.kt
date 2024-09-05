package com.example.ecommercekotlin.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.ecommercekotlin.R
import com.example.ecommercekotlin.activity.LoginActivity
import com.example.ecommercekotlin.databinding.FragmentProfileBinding
import com.example.ecommercekotlin.model.user.User
import com.example.ecommercekotlin.utility.TokenManager
import com.example.ecommercekotlin.viewmodel.ProfileViewModel

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Back to Home Fragment
        binding.profileBack.setOnClickListener {
            //Go to the Home Fragment using the nav graph
            val navController = findNavController()
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.nav_profile, true)
                .build()
            navController.navigate(R.id.action_nav_profile_to_nav_home, null, navOptions)
        }

        // Fetch user details
        profileViewModel.fetchUserDetails(requireContext())

        profileViewModel.userDetails.observe(viewLifecycleOwner) { user ->
            binding.userFirstname.text = user.firstName
            binding.userLastName.text = user.lastName
            binding.userAddress.text = formatAddress(user.address)
            Glide.with(this)
                .load(user.image)
                .into(binding.imageView2)
        }

        profileViewModel.error.observe(viewLifecycleOwner) { error ->
            // Handle the error (e.g., show a Toast or an error message in the UI)
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }

        //logout
        binding.logoutBtn.setOnClickListener {
            // Clear the token and refresh token from SharedPreferences
            TokenManager.clearTokens(requireContext())
            //NavBack to Login Screen
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    private fun formatAddress(address: User.Address): String {
        return "${address.address}, ${address.city}, ${address.state}, ${address.postalCode}, ${address.country}"
    }
}