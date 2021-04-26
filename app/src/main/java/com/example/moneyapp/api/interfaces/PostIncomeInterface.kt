package com.example.moneyapp.api.interfaces

import com.example.moneyapp.api.models.NewIncome
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface PostIncomeInterface {
    @Headers("Content-Type: application/json")
    @POST("transaction/income")
    fun addIncome(@Body userData: NewIncome): Call<JsonObject>
}