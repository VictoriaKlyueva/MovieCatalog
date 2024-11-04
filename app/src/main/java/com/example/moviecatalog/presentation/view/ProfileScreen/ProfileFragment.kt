package com.example.moviecatalog.presentation.view.ProfileScreen

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.moviecatalog.R
import com.example.moviecatalog.databinding.FragmentProfileBinding
import com.example.moviecatalog.presentation.view.FriendsScreen.FriendsActivity
import com.example.moviecatalog.presentation.view.WelcomeScreen.WelcomeActivity
import com.example.moviecatalog.presentation.viewModel.ProfileViewModel
import com.example.moviecatalog.presentation.viewModel.factory.ProfileViewModelFactory

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("Binding is not initialized")

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        setupViewModel()
        setupButtons()

        return binding.root
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
                } else {
                    // Показать ошибку пользователю
                    println("Ошибка выхода")
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
