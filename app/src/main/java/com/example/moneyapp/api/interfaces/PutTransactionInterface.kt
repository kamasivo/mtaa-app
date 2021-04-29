package com.example.moneyapp.api.interfaces

import com.example.moneyapp.api.models.NewBill
import com.example.moneyapp.api.models.UpdateBill
import com.example.moneyapp.api.models.UpdateTransaction
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.PUT

interface PutTransactionInterface {
    @Headers("Content-Type: application/json")
    @PUT("transaction")
    fun updateTransaction(@Body userData: UpdateTransaction): Call<JsonObject>
}