package com.example.moneyapp.api.interfaces

import com.example.moneyapp.api.models.Bill
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.GET

interface GetBillInterface {
    @Headers("Content-Type: application/json")
    @GET("bill/billId")
    fun getBill(@Body userData: Bill): Call<JsonObject>
}