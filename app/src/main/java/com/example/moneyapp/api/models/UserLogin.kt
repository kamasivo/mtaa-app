package com.example.moneyapp.api.models

import com.google.gson.annotations.SerializedName

data class UserLogin (
        @SerializedName("emailAddress") val emailAddress: String?,
        @SerializedName("password") val password: String?)
