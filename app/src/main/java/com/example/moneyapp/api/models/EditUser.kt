package com.example.moneyapp.api.models

import com.google.gson.annotations.SerializedName

data class EditUser (
    @SerializedName("emailAddress") val emailAddress: String?,
    @SerializedName("fullName") val fullName: String?
)