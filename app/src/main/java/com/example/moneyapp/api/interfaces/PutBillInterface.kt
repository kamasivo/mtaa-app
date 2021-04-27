package com.example.moneyapp.api.interfaces

import com.example.moneyapp.api.models.NewBill
import com.example.moneyapp.api.models.UpdateBill
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.PUT

interface PutBillInterface {
    @Headers("Content-Type: application/json")
    @PUT("bill")
    fun updateBill(@Body userData: UpdateBill): Call<JsonObject>
}