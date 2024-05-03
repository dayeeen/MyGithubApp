package com.dicoding.mygithubapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteUsersDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(note: FavoriteUsers)
    @Delete
    fun delete(note: FavoriteUsers)
    @Query("SELECT * from favoriteusers ORDER BY username ASC")
    fun getAllFavoriteUsers(): LiveData<List<FavoriteUsers>>
    @Query("SELECT EXISTS(SELECT * FROM favoriteusers WHERE username= :username AND isFavorite = 1)")
    fun isFavorite(username: String): Boolean
}