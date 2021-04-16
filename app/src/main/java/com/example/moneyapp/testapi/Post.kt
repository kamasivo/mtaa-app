package com.example.moneyapp.testapi

import com.google.gson.annotations.SerializedName

data class Post (
    @SerializedName("userId") val userId: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("id") val id: String?,
    @SerializedName("text") val text: String?
)