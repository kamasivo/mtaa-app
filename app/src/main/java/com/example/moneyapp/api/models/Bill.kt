package com.example.moneyapp.api.models

import com.google.gson.annotations.SerializedName

data class Bill(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("incomePercents") val incomePercents: Double?,
    @SerializedName("description") val description: String?,
    @SerializedName("sum") val sum: Double?
)

