package com.dicoding.mygithubapp.ui.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.mygithubapp.data.database.FavoriteUsers
import com.dicoding.mygithubapp.databinding.ListFavsBinding
import com.dicoding.mygithubapp.ui.activity.DetailActivity

class FavoriteUserAdapter: ListAdapter<FavoriteUsers, FavoriteUserAdapter.MyViewHolder>(
    DIFF_CALLBACK
) {
    inner class MyViewHolder(private val binding: ListFavsBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(itemName: FavoriteUsers) {
            Glide.with(binding.root)
                .load(itemName.avatarUrl)
                .into(binding.userAvatar)
            binding.userNickname.text = itemName.username
            binding.userBio.text = itemName.bio ?: "Pengguna ini belum memasang bio"
            binding.userFolls.text = "${itemName.followersCount} | ${itemName.followingCount}"
            binding.root.setOnClickListener {
                val intentDetail = Intent(binding.root.context, DetailActivity::class.java)
                intentDetail.putExtra("ID", itemName.name)
                intentDetail.putExtra("AVATAR", itemName.avatarUrl)
                intentDetail.putExtra("USERNAME", itemName.username)
                intentDetail.putExtra("BIO", itemName.bio)
                binding.root.context.startActivity(intentDetail)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ListFavsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FavoriteUsers>() {
            override fun areItemsTheSame(oldItem: FavoriteUsers, newItem: FavoriteUsers): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: FavoriteUsers, newItem: FavoriteUsers): Boolean {
                return oldItem == newItem
            }
        }
    }
}