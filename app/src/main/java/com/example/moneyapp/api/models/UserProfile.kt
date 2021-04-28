package com.example.moneyapp.api.models

import com.google.gson.annotations.SerializedName

// model exact as is in db
data class UserProfile (
    @SerializedName("emailAddress") val emailAddress: String?,
    @SerializedName("fullName") val fullName: String?,
    @SerializedName("image") val image: String?,
)
