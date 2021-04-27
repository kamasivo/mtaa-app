package com.example.moneyapp.api.interfaces

import com.example.moneyapp.api.models.BillResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface GetBillByIdInterface {
    @Headers("Content-Type: application/json")
    @GET("/bill/{id}")
    fun getBill(@Path("id") id: Int?): Call<BillResponse>
}