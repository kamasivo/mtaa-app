package com.example.moneyapp.api.interfaces

import com.example.moneyapp.api.models.NewExpenditure
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface PostExpenditureInterface {
    @Headers("Content-Type: application/json")
    @POST("transaction/expenditure")
    fun addExpenditure(@Body userData: NewExpenditure): Call<JsonObject>
}