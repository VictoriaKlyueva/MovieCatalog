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
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.moviecatalog.common.Constants.BINDING_IS_NOT_INITIALIZED
import com.example.moviecatalog.common.Constants.PICK_IMAGE_REQUEST
import com.example.moviecatalog.data.model.main.UserShortModel

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

    private fun setupAvatar() {
        binding.avatar.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.dialog_avatar, null)
            val editText = dialogView.findViewById<AppCompatEditText>(R.id.editTextAvatarUrl)

            val dialog = AlertDialog.Builder(requireContext())
                .setView(dialogView)
                .create()

            dialog.setOnShowListener {
                dialog.window?.setBackgroundDrawableResource(R.drawable.rounded_corners_mini)

                val layoutParams = dialog.window?.attributes
                layoutParams?.width = (resources.displayMetrics.widthPixels * 0.9).toInt()
                layoutParams?.height = WindowManager.LayoutParams.WRAP_CONTENT
                dialog.window?.attributes = layoutParams
            }

            dialogView.findViewById<Button>(R.id.buttonConfirm).setOnClickListener {
                val avatarUrl = editText.text.toString()
                if (avatarUrl.isNotEmpty()) {
                    val currentProfile = viewModel.profile.value

                    if (currentProfile != null) {
                        val newProfile = currentProfile.copy(avatarLink = avatarUrl)
                        viewModel.editProfileData(newProfile)
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Текущий профиль не найден",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Введите значение",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                dialog.dismiss()
            }

            dialogView.findViewById<Button>(R.id.buttonCancel).setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()
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
                .transform(CircleCrop())
                .into(binding.avatar)
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            requireActivity(),
            ProfileViewModelFactory(requireContext())
        )[ProfileViewModel::class.java]

        viewModel.fetchFriends()
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

    private fun loadFriendAvatars(friends: List<UserShortModel>) {
        if (friends.isNotEmpty()) {
            loadAvatar(binding.friendAvatar1, friends[0].avatar)
        }
        if (friends.size > 1) {
            loadAvatar(binding.friendAvatar2, friends[1].avatar)
        }
        if (friends.size > 2) {
            loadAvatar(binding.friendAvatar3, friends[2].avatar)
        }
    }

    private fun loadAvatar(imageView: android.widget.ImageView, avatarUrl: String?) {
        if (avatarUrl != null) {
            println("должна поставиться аватарка")
            Glide.with(binding.root.context)
                .load(avatarUrl)
                .transform(CircleCrop())
                .into(imageView)
        } else {
            println("не должна поставиться аватарка")
            imageView.setImageResource(R.drawable.avatar_default)
        }
    }

    private fun observeProfileData() {
        viewModel.profile.observe(viewLifecycleOwner) { profile ->
            profile?.let {
                fillFields(it)
            }
        }

        viewModel.friends.observe(viewLifecycleOwner) { friends ->
            loadFriendAvatars(friends)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

