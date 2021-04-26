package com.example.moneyapp.api.interfaces

import com.example.moneyapp.api.models.NewType
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface PostTypeInterface {
    @Headers("Content-Type: application/json")
    @POST("/category/income")
    fun addType(@Body userData: NewType): Call<JsonObject>
}