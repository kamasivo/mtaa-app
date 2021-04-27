package com.example.moneyapp.api.models

import com.google.gson.annotations.SerializedName

class NewSplitIncome (
    @SerializedName("sum") val sum: Int?,
    @SerializedName("categoryId") val categoryId: Int?
)