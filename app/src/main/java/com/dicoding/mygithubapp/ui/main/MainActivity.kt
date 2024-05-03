package com.dicoding.mygithubapp.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.mygithubapp.data.response.UserDetail
import com.dicoding.mygithubapp.data.response.UserResponse
import com.dicoding.mygithubapp.data.retrofit.ApiConfig
import com.dicoding.mygithubapp.databinding.ActivityMainBinding
import com.dicoding.mygithubapp.setting.SettingThemeActivity
import com.dicoding.mygithubapp.setting.SettingThemePreferences
import com.dicoding.mygithubapp.setting.SettingThemeViewModel
import com.dicoding.mygithubapp.setting.SettingThemeViewModelFactory
import com.dicoding.mygithubapp.setting.dataStore
import com.dicoding.mygithubapp.ui.activity.FavoriteUserActivity
import com.dicoding.mygithubapp.ui.adapter.UserAdapter
import com.dicoding.mygithubapp.ui.viewmodel.MainViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val userViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingThemePreferences.getInstance(application.dataStore)
        val settingViewModel =
            ViewModelProvider(this, SettingThemeViewModelFactory(pref))[SettingThemeViewModel::class.java]

        settingViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    searchBar.setText(searchView.text)
                    searchView.hide()
                    userViewModel.setReviewData(searchBar.text.toString())
                    userViewModel.listUser.observe(this@MainActivity) {
                        if (it.isNullOrEmpty()) {
                            showNotFound(true)
                        } else {
                            showNotFound(false)
                        }
                    }
                    false
                }
        }


        val mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[MainViewModel::class.java]
        mainViewModel.listUser.observe(this) {
            setReviewData(it)
        }
        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        //ListFavs
        val listFavoritesImageView: ImageView = binding.listFavorites
        listFavoritesImageView.setOnClickListener {
            openFavoriteUserActivity()
        }

        //DarkMode
        val darkModeImageView: ImageView = binding.darkMode
        darkModeImageView.setOnClickListener{
            openDarkModeActivity()
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvUserList.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUserList.addItemDecoration(itemDecoration)
        findUser()
    }

    private fun openFavoriteUserActivity() {
        val intent = Intent(this, FavoriteUserActivity::class.java)
        startActivity(intent)
    }

    private fun openDarkModeActivity() {
        val intent = Intent(this, SettingThemeActivity::class.java)
        startActivity(intent)
    }

    private fun findUser() {
        showLoading(true)
        val randomIndex = kotlin.random.Random.nextInt(userList.size)
        val randomUsername = userList[randomIndex]
        val client = ApiConfig.getApiService().getUser(randomUsername)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>,
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        setReviewData(responseBody.details)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun setReviewData(item: List<UserDetail>) {
        val adapter = UserAdapter()
        adapter.submitList(item)
        binding.rvUserList.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showNotFound(isDataNotFound: Boolean) {
        binding.apply {
            if (isDataNotFound) {
                rvUserList.visibility = View.GONE
                errorMessage.visibility = View.VISIBLE
            } else {
                rvUserList.visibility = View.VISIBLE
                errorMessage.visibility = View.GONE
            }
        }
    }

    companion object {
        private const val TAG = "MainActivity"
        private val userList = listOf(
            "a", "i", "u", "e", "o"
        )
    }
}
