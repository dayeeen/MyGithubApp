package com.dicoding.mygithubapp.data.retrofit

import com.dicoding.mygithubapp.data.response.UserDetail
import com.dicoding.mygithubapp.data.response.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun getUser(@Query("q") query: String): Call<UserResponse>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<UserDetail>

    @GET("users/{username}/followers")
    fun getUserFollowers(@Path("username") username: String): Call<List<UserDetail>>

    @GET("users/{username}/following")
    fun getUserFollowing(@Path("username") username: String): Call<List<UserDetail>>
}