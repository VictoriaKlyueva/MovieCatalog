package com.example.moviecatalog.presentation.view.FriendsScreen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviecatalog.databinding.ActivityFriendsBinding
import com.example.moviecatalog.domain.model.FakeFriends
import com.example.moviecatalog.presentation.view.FeedActivity
import com.example.moviecatalog.presentation.viewModel.FriendsViewModel

class FriendsActivity : AppCompatActivity() {
    private var _binding: ActivityFriendsBinding? = null
    private val binding get() = _binding ?:
    throw IllegalStateException("Binding is not initialized")

    private lateinit var friendsViewModel: FriendsViewModel
    private lateinit var friendsAdapter: FriendsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFriendsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupRecyclerView()
        setupButtons()
    }

    private fun setupButtons() {
        binding.backButton.setOnClickListener {
            val intent = Intent(this, FeedActivity::class.java).apply {
                putExtra(FeedActivity.EXTRA_INITIAL_FRAGMENT, "profile")
            }
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        val gridLayoutManager = GridLayoutManager(this, 3)
        binding.recyclerViewFavorites.layoutManager = gridLayoutManager

        friendsAdapter = FriendsAdapter(FakeFriends.friends)
        binding.recyclerViewFavorites.adapter = friendsAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}