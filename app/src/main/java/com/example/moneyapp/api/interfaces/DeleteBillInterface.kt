package com.example.moneyapp.api.interfaces

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.Headers
import retrofit2.http.Path

interface DeleteBillInterface {
    @Headers("Content-Type: application/json")
    @DELETE("bill/{id}")
    fun deleteBill(@Path("id") id: Int?): Call<JsonObject>
}