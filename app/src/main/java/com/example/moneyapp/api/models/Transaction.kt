package com.example.moneyapp.api.models

import com.google.gson.annotations.SerializedName

data class Transaction (
        @SerializedName("id") val id: Int?,
        @SerializedName("sum") val sum: Double?,
        @SerializedName("categoryId") val categoryId: Int?
)