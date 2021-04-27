package com.example.moneyapp.api.models

import com.google.gson.annotations.SerializedName

data class Category (
    @SerializedName("createdAt") val createdAt: Double?,
    @SerializedName("updatedAt") val updatedAt: Double?,
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
)