package com.dicoding.mygithubapp.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.dicoding.mygithubapp.data.response.UserDetail
import com.dicoding.mygithubapp.databinding.ListUserBinding
import com.dicoding.mygithubapp.ui.activity.DetailActivity

class UserAdapter: ListAdapter<UserDetail, UserAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ListUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    class MyViewHolder(private val binding: ListUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UserDetail) {
            Glide.with(binding.root)
                .load(item.avatarUrl)
                .into(binding.userAvatar)
            binding.userNickname.text = item.login
            binding.userLink.text = item.htmlUrl
            binding.root.setOnClickListener {
                val intentDetail = Intent(binding.root.context, DetailActivity::class.java)
                intentDetail.putExtra("ID", item.id)
                intentDetail.putExtra("USERNAME", item.login)
                intentDetail.putExtra("AVATAR", item.avatarUrl)
                intentDetail.putExtra("LINK", item.htmlUrl)
                binding.root.context.startActivity(intentDetail)
            }
        }
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
