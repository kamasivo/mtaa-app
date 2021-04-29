package com.example.moneyapp.api.interfaces

import com.example.moneyapp.api.models.BillResponse
import com.example.moneyapp.api.models.UserProfile
import com.example.moneyapp.api.models.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface GetUserInterface {
    @Headers("Content-Type: application/json")
    @GET("user")
    fun getUser(): Call<UserResponse>
}