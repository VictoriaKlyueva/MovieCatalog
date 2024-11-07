package com.example.moviecatalog.presentation.view.FriendsScreen

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.moviecatalog.data.model.main.UserShortModel
import com.example.moviecatalog.databinding.ItemFriendBinding
import com.example.moviecatalog.presentation.viewModel.FriendsViewModel

class FriendsAdapter(
    private val viewModel: FriendsViewModel,
    private var friends: List<UserShortModel>
) : RecyclerView.Adapter<FriendsAdapter.FriendViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val binding = ItemFriendBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return FriendViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        val profile = friends[position]
        holder.bind(profile, position)
    }

    override fun getItemCount(): Int = friends.size

    inner class FriendViewHolder(private val binding: ItemFriendBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("DefaultLocale", "NotifyDataSetChanged")
        fun bind(profile: UserShortModel, position: Int) {
            if (profile.avatar != null) {
                Glide.with(binding.root.context)
                    .load(profile.avatar)
                    .transform(CircleCrop())
                    .into(binding.friendAvatar)
            }

            binding.friendName.text = profile.nickName

            binding.deleteFriendButton.setOnClickListener {
                viewModel.removeFriend(friends[position])
                notifyDataSetChanged()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateFriends(friendList: List<UserShortModel>) {
        this.friends = friendList
        notifyDataSetChanged()
    }
}
