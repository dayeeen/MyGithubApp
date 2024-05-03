package com.dicoding.mygithubapp.data.response

import com.google.gson.annotations.SerializedName

data class UserDetail(

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("html_url")
    val htmlUrl: String,

    @field:SerializedName("bio")
    val bio: String,

    @field:SerializedName("created_at")
    val createdAt: String,

    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("blog")
    val blog: String,

    @field:SerializedName("updated_at")
    val updatedAt: String,

    @field:SerializedName("email")
    val email: Any,

    @field:SerializedName("followers_url")
    val followersUrl: String,

    @field:SerializedName("followers")
    val followers: Int,

    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    @field:SerializedName("following")
    val following: Int,

    @field:SerializedName("name")
    val name: String? = "Pengguna ini belum memasang nama",
)