package com.dicoding.mygithubapp.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.mygithubapp.data.response.UserDetail
import com.dicoding.mygithubapp.databinding.ListFollsBinding
import com.dicoding.mygithubapp.ui.activity.DetailActivity

class DetailUserAdapter: ListAdapter<UserDetail, DetailUserAdapter.MyViewHolder>(DIFF_CALLBACK) {
    class MyViewHolder(private val binding: ListFollsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemName: UserDetail) {
            binding.userNickname.text = itemName.login
            binding.userLink.text = itemName.htmlUrl
            Glide.with(binding.root)
                .load(itemName.avatarUrl)
                .into(binding.userAvatar)
            binding.root.setOnClickListener {
                val intentDetail = Intent(binding.root.context, DetailActivity::class.java)
                intentDetail.putExtra("ID", itemName.id)
                intentDetail.putExtra("USERNAME", itemName.login)
                intentDetail.putExtra("AVATAR", itemName.avatarUrl)
                intentDetail.putExtra("LINK", itemName.htmlUrl)
                binding.root.context.startActivity(intentDetail)
            }
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ListFollsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserDetail>() {
            override fun areItemsTheSame(oldItem: UserDetail, newItem: UserDetail): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: UserDetail, newItem: UserDetail): Boolean {
                return oldItem == newItem
            }
        }
    }
}