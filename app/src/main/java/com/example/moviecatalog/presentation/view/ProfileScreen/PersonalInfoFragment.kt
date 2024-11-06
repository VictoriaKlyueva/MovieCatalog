package com.example.moviecatalog.presentation.view.ProfileScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.moviecatalog.data.model.ProfileModel
import com.example.moviecatalog.databinding.FragmentPersonalInfoBinding
import com.example.moviecatalog.domain.utils.DateHelper
import com.example.moviecatalog.presentation.viewModel.ProfileViewModel
import com.example.moviecatalog.presentation.viewModel.factory.ProfileViewModelFactory

class PersonalInfoFragment : Fragment() {

    private var _binding: FragmentPersonalInfoBinding? = null
    private val binding get() = _binding ?:
        throw IllegalStateException("Binding is not initialized")

    private lateinit var viewModel: ProfileViewModel

    private val dateHelper = DateHelper()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPersonalInfoBinding.inflate(inflater, container, false)

        setupViewModel()
        getProfileData()
        observeProfileData()

        return binding.root
    }

    private fun fillFields(profile: ProfileModel) {
        binding.textLogin.setText(profile.nickName)
        binding.textEmail.setText(profile.email)
        binding.textName.setText(profile.name)
        binding.textDateOfBirth.setText(dateHelper.convertFromDateTimezones(profile.birthDate))
        fillToggles(profile)
    }

    private fun fillToggles(profile: ProfileModel) {
        val genderValue = profile.gender
        when (genderValue) {
            0 -> {
                binding.manToggle.isChecked = true
                binding.womanToggle.isChecked = false
            }
            1 -> {
                binding.manToggle.isChecked = false
                binding.womanToggle.isChecked = true
            }
            else -> {
                binding.manToggle.isChecked = false
                binding.womanToggle.isChecked = false
            }
        }
    }

    private fun getProfileData() {
        viewModel.getProfileData { _, error ->
            if (error != null) {
                println("Error retrieving profile: $error")
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

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            requireActivity(),
            ProfileViewModelFactory(requireContext())
        )[ProfileViewModel::class.java]
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}