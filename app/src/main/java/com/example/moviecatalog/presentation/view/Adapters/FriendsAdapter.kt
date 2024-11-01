package com.example.moviecatalog.presentation.view.Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.moviecatalog.R
import com.example.moviecatalog.data.model.ProfileModel
import com.example.moviecatalog.databinding.ItemFriendBinding

class FriendsAdapter(
    private var profiles: List<ProfileModel>
): RecyclerView.Adapter<FriendsAdapter.FriendViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val binding = ItemFriendBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return FriendViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FriendsAdapter.FriendViewHolder, position: Int) {
        val profile = profiles[position]
        holder.bind(profile)
    }

    override fun getItemCount(): Int = profiles.size

    inner class FriendViewHolder(private val binding: ItemFriendBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("DefaultLocale")
        fun bind(profile: ProfileModel) {

            // Avatar
            if (profile.avatarLink != null) {
                Glide.with(binding.root.context)
                    .load(profile.avatarLink)
                    .transform(CircleCrop())
                    .into(binding.friendAvatar)
            }

            // Name
            binding.friendName.text = profile.name
        }
    }
}