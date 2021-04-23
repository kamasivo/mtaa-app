package com.example.moneyapp.api.interfaces

import com.example.moneyapp.api.models.BillsArray
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface GetBillInterface {
    @Headers("Content-Type: application/json")
    @GET("bill/users")
    fun getBills(): Call<BillsArray>
}