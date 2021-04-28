package com.example.moneyapp.api.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserResponse (
    @SerializedName("user")
    @Expose
    val user: List<UserProfile>
)