package com.example.moneyapp.api.interfaces

import com.example.moneyapp.api.models.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface PostUserInterface {
    @Headers("Content-Type: application/json")
    @POST("user")
    fun addUser(@Body userData: User): Call<String>
}