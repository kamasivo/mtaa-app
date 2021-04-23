package com.example.moneyapp.api.models

import com.google.gson.annotations.SerializedName

data class Bill (
    @SerializedName("name") val name: String?,
    @SerializedName("incomePercents") val incomePercents: Int?,
    @SerializedName("description") val description: String?,
    @SerializedName("sum") val sum: Int?
)