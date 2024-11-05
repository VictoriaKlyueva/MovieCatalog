package com.example.moviecatalog.presentation.view.ProfileScreen

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.moviecatalog.R
import com.example.moviecatalog.data.model.MovieElementModel
import com.example.moviecatalog.data.model.ProfileModel
import com.example.moviecatalog.databinding.FragmentProfileBinding
import com.example.moviecatalog.domain.utils.DateHelper
import com.example.moviecatalog.presentation.view.FriendsScreen.FriendsActivity
import com.example.moviecatalog.presentation.view.WelcomeScreen.WelcomeActivity
import com.example.moviecatalog.presentation.viewModel.ProfileViewModel
import com.example.moviecatalog.presentation.viewModel.factory.ProfileViewModelFactory
import java.util.Calendar
import java.util.Date

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("Binding is not initialized")

    private lateinit var viewModel: ProfileViewModel
    private val dateHelper = DateHelper()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        setupViewModel()

        setupButtons()
        setupGreeting()

        getProfileData()
        observeProfileData()

        return binding.root
    }

    private fun setupGreeting() {
        val hour = dateHelper.getCurrentTime()

        val greetingText = when (hour) {
            in 6..11 -> getString(R.string.good_morning) + ","
            in 12..17 -> getString(R.string.good_day) + ","
            in 18..23 -> getString(R.string.good_evening) + ","
            else -> getString(R.string.good_night) + ","
        }

        binding.greetingText.text = greetingText
    }


    private fun fillFields(profile: ProfileModel) {
        binding.profileName.text = profile.name
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            requireActivity(),
            ProfileViewModelFactory(requireContext())
        )[ProfileViewModel::class.java]
    }

    private fun setupButtons() {
        binding.myFriends.setOnClickListener {
            val intent = Intent(requireContext(), FriendsActivity::class.java)
            startActivity(intent)
        }

        binding.logoutButton.setOnClickListener {
            viewModel.onLogoutButtonClicked { success ->
                if (success) {
                    val intent = Intent(requireContext(), WelcomeActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                } else {
                    println("Ошибка выхода")
                }
            }
        }
    }

    private fun getProfileData() {
        viewModel.getProfileData { profile, error ->
            if (error != null) {
                println("Error retrieving profile: $error")
            } else {
                println("Успешный успех!")
            }
        }
    }

    private fun observeProfileData() {
        viewModel.profile.observe(viewLifecycleOwner) { profile ->
            profile?.let {
                fillFields(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

