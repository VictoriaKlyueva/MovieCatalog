package com.example.moviecatalog.presentation.view.FriendsScreen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviecatalog.common.Constants.EXTRA_INITIAL_FRAGMENT
import com.example.moviecatalog.data.model.main.UserShortModel
import com.example.moviecatalog.databinding.ActivityFriendsBinding
import com.example.moviecatalog.presentation.view.FeedActivity
import com.example.moviecatalog.presentation.viewModel.FriendsViewModel
import com.example.moviecatalog.presentation.viewModel.factory.FriendsViewModelFactory

class FriendsActivity : AppCompatActivity() {
    private var _binding: ActivityFriendsBinding? = null
    private val binding get() =
        _binding ?: throw IllegalStateException("Binding is not initialized")

    private lateinit var viewModel: FriendsViewModel
    private lateinit var friendsAdapter: FriendsAdapter

    private var friends: List<UserShortModel> by mutableStateOf(mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFriendsBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        setupViewModel()
        observeFriends()

        setupRecyclerView()
        setupButtons()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            FriendsViewModelFactory(this)
        )[FriendsViewModel::class.java]
        viewModel.fetchFriends()
    }

    private fun observeFriends() {
        viewModel.friends.observe(this) { friendList ->
            friends = friendList ?: emptyList()
            friendsAdapter.updateFriends(friends)
        }
    }

    private fun setupButtons() {
        binding.backButton.setOnClickListener {
            val intent = Intent(this, FeedActivity::class.java).apply {
                putExtra(EXTRA_INITIAL_FRAGMENT, "profile")
            }
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        val gridLayoutManager = GridLayoutManager(this, 3)
        binding.recyclerViewFavorites.layoutManager = gridLayoutManager

        friendsAdapter = FriendsAdapter(viewModel, friends)
        binding.recyclerViewFavorites.adapter = friendsAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}