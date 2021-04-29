package com.example.moneyapp.api.models

import com.google.gson.annotations.SerializedName

data class UpdateTransaction (
        @SerializedName("transactionId") val transactionId: Int?,
        @SerializedName("sum") val sum: Double?
)