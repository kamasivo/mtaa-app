package com.example.moneyapp.api.models

import com.google.gson.annotations.SerializedName

class NewExpenditure (
    @SerializedName("sum") val sum: Int?,
    @SerializedName("billId") val billId: Int?,
    @SerializedName("categoryId") val categoryId: Int?
)