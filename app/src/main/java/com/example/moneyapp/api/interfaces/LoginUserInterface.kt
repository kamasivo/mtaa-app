package com.example.moneyapp.api.interfaces

import com.example.moneyapp.api.models.UserLogin
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.PUT

interface LoginUserInterface {
    @Headers("Content-Type: application/json")
    @PUT("user/login")
    fun loginUser(@Body userData: UserLogin): Call<JsonObject>
}