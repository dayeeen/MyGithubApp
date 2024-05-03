package com.dicoding.mygithubapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.mygithubapp.data.database.FavoriteUsers
import com.dicoding.mygithubapp.data.repository.FavoriteUsersRepository

class FavoriteUserViewModel(application: Application) : ViewModel() {
    private val favUsersRepository: FavoriteUsersRepository = FavoriteUsersRepository(application)
    fun getAllFavoriteUsers(): LiveData<List<FavoriteUsers>> = favUsersRepository.getAllFavorite()
    init {
        getAllFavoriteUsers()
    }
}