package com.example.moneyapp.api.interfaces

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface PostBillInterface {
    @Headers("Content-Type: application/json")
    @POST("bill")
    fun addBill(@Body userData: Unit): Call<JsonObject>
}