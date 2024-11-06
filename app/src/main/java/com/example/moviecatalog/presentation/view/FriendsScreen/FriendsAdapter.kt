package com.example.moviecatalog.presentation.view.FriendsScreen

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.moviecatalog.data.model.main.ProfileModel
import com.example.moviecatalog.databinding.ItemFriendBinding

class FriendsAdapter(
    private var profiles: MutableList<ProfileModel>
) : RecyclerView.Adapter<FriendsAdapter.FriendViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val binding = ItemFriendBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return FriendViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        val profile = profiles[position]
        holder.bind(profile, position)
    }

    override fun getItemCount(): Int = profiles.size

    inner class FriendViewHolder(private val binding: ItemFriendBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("DefaultLocale")
        fun bind(profile: ProfileModel, position: Int) {
            // Avatar
            if (profile.avatarLink != null) {
                Glide.with(binding.root.context)
                    .load(profile.avatarLink)
                    .transform(CircleCrop())
                    .into(binding.friendAvatar)
            }

            // Name
            binding.friendName.text = profile.name

            // Минус друг
            binding.deleteFriendButton.setOnClickListener {
                removeFriend(position)
            }
        }
    }

    private fun removeFriend(position: Int) {
        profiles.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, profiles.size)
    }
}
