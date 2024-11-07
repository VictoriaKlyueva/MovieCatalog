package com.example.moviecatalog.presentation.view.ProfileScreen

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.moviecatalog.R
import com.example.moviecatalog.data.model.main.ProfileModel
import com.example.moviecatalog.databinding.FragmentProfileBinding
import com.example.moviecatalog.domain.utils.DateHelper
import com.example.moviecatalog.presentation.view.FriendsScreen.FriendsActivity
import com.example.moviecatalog.presentation.view.WelcomeScreen.WelcomeActivity
import com.example.moviecatalog.presentation.viewModel.ProfileViewModel
import com.example.moviecatalog.presentation.viewModel.factory.ProfileViewModelFactory
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import com.example.moviecatalog.common.Constants.BINDING_IS_NOT_INITIALIZED
import com.example.moviecatalog.common.Constants.PICK_IMAGE_REQUEST

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() =
        _binding ?: throw IllegalStateException(BINDING_IS_NOT_INITIALIZED)

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
        setupAvatar()

        observeProfileData()

        return binding.root
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST &&
            resultCode == AppCompatActivity.RESULT_OK &&
            data != null) {
            val imageUri: Uri? = data.data
            imageUri?.let {
                val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(
                    requireActivity().contentResolver,
                    imageUri
                )
                binding.avatar.setImageBitmap(bitmap)
            }
        }
    }

    private fun setupAvatar() {
        binding.avatar.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }
    }

    private fun setupGreeting() {
        val hour = dateHelper.getCurrentTime()

        val greetingText = when (hour) {
            in 6..11 -> getString(R.string.good_morning)
            in 12..17 -> getString(R.string.good_day)
            in 18..23 -> getString(R.string.good_evening)
            else -> getString(R.string.good_night)
        }

        binding.greetingText.text = greetingText
    }

    private fun fillFields(profile: ProfileModel) {
        binding.profileName.text = profile.name

        if (profile.avatarLink != null) {
            Glide.with(binding.root.context)
                .load(profile.avatarLink)
                .transform(RoundedCorners(90))
                .into(binding.avatar)
        }
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

