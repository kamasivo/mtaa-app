package com.example.moneyapp.api.models

import com.google.gson.annotations.SerializedName

class NewCategory (
    @SerializedName("name") val name: String?,
    @SerializedName("billId") val billId: Int?
)