package com.dicoding.mygithubapp.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.mygithubapp.data.database.FavoriteUsers
import com.dicoding.mygithubapp.data.repository.FavoriteUsersRepository
import com.dicoding.mygithubapp.data.response.UserDetail
import com.dicoding.mygithubapp.data.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : ViewModel() {
    private val favUsers: FavoriteUsersRepository = FavoriteUsersRepository(application)

    private val _userDetail = MutableLiveData<FavoriteUsers>()
    val userDetail: LiveData<FavoriteUsers> = _userDetail

    private val _loadingScreen = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loadingScreen

    private val _userFollower = MutableLiveData<List<UserDetail>>()
    val userFollower: LiveData<List<UserDetail>> = _userFollower

    private val _userFollowing = MutableLiveData<List<UserDetail>>()
    val userFollowing: LiveData<List<UserDetail>> = _userFollowing


    private var isloaded = false
    private var isfollowerloaded = false
    private var isfollowingloaded = false

    companion object {
        private const val TAG = "DetailViewModel"
    }

    fun getDetailUser(username: String) {
        if (!isloaded) {
            _loadingScreen.value = true
            val client = ApiConfig.getApiService().getDetailUser(username)
            client.enqueue(object : Callback<UserDetail> {
                override fun onResponse(
                    call: Call<UserDetail>,
                    response: Response<UserDetail>,
                ) {
                    _loadingScreen.value = false
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            viewModelScope.launch {
                                val isFavorite = favUsers.getIsFavorite(responseBody.login)
                                val currentUser =
                                    FavoriteUsers(
                                        username = responseBody.login,
                                        name = responseBody.name,
                                        htmlUrl = responseBody.htmlUrl,
                                        avatarUrl = responseBody.avatarUrl,
                                        bio = responseBody.bio,
                                        followersCount = responseBody.followers.toString(),
                                        followingCount = responseBody.following.toString(),
                                        isFavorite = isFavorite
                                    )
                                _userDetail.postValue(currentUser)
                            }
                        }
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<UserDetail>, t: Throwable) {
                    _loadingScreen.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            })
            isloaded = true
        }
    }

    fun getUserFollowing(username: String) {
        if (!isfollowingloaded) {
            _loadingScreen.value = true
            val client = ApiConfig.getApiService().getUserFollowing(username)
            client.enqueue(object : Callback<List<UserDetail>> {
                override fun onResponse(
                    call: Call<List<UserDetail>>,
                    response: Response<List<UserDetail>>,
                ) {
                    _loadingScreen.value = false
                    if (response.isSuccessful) {
                        _userFollowing.postValue(response.body())
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<List<UserDetail>>, t: Throwable) {
                    _loadingScreen.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            })
            isfollowingloaded = true
        }
    }

    fun getUserFollower(username: String) {
        if (!isfollowerloaded) {
            _loadingScreen.value = true
            val client = ApiConfig.getApiService().getUserFollowers(username)
            client.enqueue(object : Callback<List<UserDetail>> {
                override fun onResponse(
                    call: Call<List<UserDetail>>,
                    response: Response<List<UserDetail>>,
                ) {
                    _loadingScreen.value = false
                    if (response.isSuccessful) {
                        _userFollower.postValue(response.body())

                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<List<UserDetail>>, t: Throwable) {
                    _loadingScreen.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            })
            isfollowerloaded = true
        }
    }

    fun insertFavorite(favoriteUser: FavoriteUsers) {
        favUsers.insert(favoriteUser)
    }

    fun deleteFavorite(favoriteUser: FavoriteUsers) {
        favUsers.delete(favoriteUser)
    }
}