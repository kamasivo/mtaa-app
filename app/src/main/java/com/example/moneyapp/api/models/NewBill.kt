package com.example.moneyapp.api.models

import com.google.gson.annotations.SerializedName

class NewBill (
    @SerializedName("name") val name: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("incomePercents") val incomePercents: Int?,
    @SerializedName("sum") val sum: Int?
)