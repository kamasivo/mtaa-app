package com.example.moneyapp.api.models

import com.google.gson.annotations.SerializedName

// model exact as is in db
data class Password (
    @SerializedName("password") val password: String?,
    @SerializedName("newPassword") val newPassword: String?
)
