package com.example.moneyapp.api.interfaces

import com.example.moneyapp.api.models.NewSplitIncome
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface PostSplitIncomeInterface {
    @Headers("Content-Type: application/json")
    @POST("transaction/splitincome")
    fun addSplitIncome(@Body userData: NewSplitIncome): Call<JsonObject>
}