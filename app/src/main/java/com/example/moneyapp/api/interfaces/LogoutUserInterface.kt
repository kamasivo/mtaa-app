package com.example.moneyapp.api.interfaces

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Headers
import retrofit2.http.PUT

interface LogoutUserInterface {
    @Headers("Content-Type: application/json")
    @PUT("user/logout")
    fun loginUser(): Call<JsonObject>
}