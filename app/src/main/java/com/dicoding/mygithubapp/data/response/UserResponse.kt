package com.dicoding.mygithubapp.data.response

import com.google.gson.annotations.SerializedName

data class UserResponse(

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("html_url")
	val htmlUrl: String,

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("total_count")
	val totalCount: Int,

	@field:SerializedName("items")
	val details: List<UserDetail>

)

