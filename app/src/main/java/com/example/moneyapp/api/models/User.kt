package com.example.moneyapp.api.models

import com.google.gson.annotations.SerializedName

// model exact as is in db
data class User (
    @SerializedName("emailAddress") val emailAddress: String?,
    @SerializedName("password") val password: String?,
    @SerializedName("fullName") val fullName: String?
)
