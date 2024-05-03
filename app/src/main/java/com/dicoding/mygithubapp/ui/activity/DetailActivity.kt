package com.dicoding.mygithubapp.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.mygithubapp.R
import com.dicoding.mygithubapp.data.database.FavoriteUsers
import com.dicoding.mygithubapp.databinding.ActivityDetailBinding
import com.dicoding.mygithubapp.ui.viewmodel.DetailViewModel
import com.dicoding.mygithubapp.ui.adapter.SectionsPagerAdapter
import com.dicoding.mygithubapp.ui.viewmodel.ViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance(application)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val username = intent.getStringExtra("USERNAME")
        val avatar = intent.getStringExtra("AVATAR")

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (username != null) {
            val sectionPagerAdapter = SectionsPagerAdapter(this, username)
            val viewPager: ViewPager2 = findViewById(R.id.view_pager)
            viewPager.adapter = sectionPagerAdapter
            val tabs: TabLayout = findViewById(R.id.tabs)
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
            supportActionBar?.elevation = 0f
        }
        if (username != null) {
            detailViewModel.getDetailUser(username)
        }

        detailViewModel.userDetail.observe(this) {
            if (it != null) {
                Glide.with(this@DetailActivity)
                    .load(it.avatarUrl)
                    .centerCrop()
                    .into(binding.imgUserPhoto)
                binding.tvName.text = it.name ?: "Pengguna ini belum memasang nama"
                binding.tvUserName.text = it.username
                binding.tvBio.text = it.bio ?: "Pengguna ini belum memasang bio"
                binding.tvFollower.text = "${it.followersCount} Follower"
                binding.tvFollowing.text = "${it.followingCount} Following"
                binding.fabAdd.contentDescription = it.isFavorite.toString()

                binding.apply {
                    if (!it.isFavorite) {
                        fabAdd.setImageDrawable(
                            ContextCompat.getDrawable(
                                this@DetailActivity, R.drawable.ic_add
                            )
                        )
                    } else {
                        fabAdd.setImageDrawable(
                            ContextCompat.getDrawable(
                                this@DetailActivity, R.drawable.ic_add_filled
                            )
                        )
                    }
                }
            }
        }
        detailViewModel.loading.observe(this) {
            showLoading(it)
        }

        binding.apply {
            fabAdd.setOnClickListener {
                val userFavorite = FavoriteUsers(
                    username = tvUserName.text.toString(),
                    name = tvName.text.toString(),
                    bio = tvBio.text.toString(),
                    avatarUrl = avatar.toString(),
                    isFavorite = true,
                    followersCount = tvFollower.text.toString(),
                    followingCount = tvFollowing.text.toString()
                )

                val currentIcon = fabAdd.contentDescription
                if (currentIcon == "true") {
                    fabAdd.setImageDrawable(
                        ContextCompat.getDrawable(
                            this@DetailActivity, R.drawable.ic_add
                        )
                    )
                    detailViewModel.deleteFavorite(userFavorite)
                    fabAdd.contentDescription = "false"
                } else {
                    fabAdd.setImageDrawable(
                        ContextCompat.getDrawable(
                            this@DetailActivity, R.drawable.ic_add_filled
                        )
                    )
                    detailViewModel.insertFavorite(userFavorite)
                    fabAdd.contentDescription = "true"
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.follower,
            R.string.following
        )
    }

}