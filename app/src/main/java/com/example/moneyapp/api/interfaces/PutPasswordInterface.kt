package com.example.moneyapp.api.interfaces

import com.example.moneyapp.api.models.Password
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.PUT

interface PutPasswordInterface {
    @Headers("Content-Type: application/json")
    @PUT("user/password")
    fun updatePassword(@Body userData: Password): Call<JsonObject>
}