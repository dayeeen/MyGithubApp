package com.dicoding.mygithubapp.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.mygithubapp.data.database.FavoriteUsers
import com.dicoding.mygithubapp.data.database.FavoriteUsersDAO
import com.dicoding.mygithubapp.data.database.FavoriteUsersRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUsersRepository(application: Application) {
    private val mFavoritesUserDao: FavoriteUsersDAO
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteUsersRoomDatabase.getDatabase(application)
        mFavoritesUserDao = db.favoriteUserDao()
    }

    fun getAllFavorite(): LiveData<List<FavoriteUsers>> = mFavoritesUserDao.getAllFavoriteUsers()

    fun getIsFavorite(username: String): Boolean {
        return mFavoritesUserDao.isFavorite(username)
    }

    fun insert(favorite: FavoriteUsers) {
        executorService.execute { mFavoritesUserDao.insert(favorite) }
    }

    fun delete(favorite: FavoriteUsers) {
        executorService.execute { mFavoritesUserDao.delete(favorite) }
    }
}