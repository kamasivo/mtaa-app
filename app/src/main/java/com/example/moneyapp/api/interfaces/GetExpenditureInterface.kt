package com.example.moneyapp.api.interfaces

import com.example.moneyapp.api.models.BillsArray
import com.example.moneyapp.api.models.ExpenditureTransactionArray
import com.example.moneyapp.api.models.TransactionArray
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface GetExpenditureInterface {
    @Headers("Content-Type: application/json")
    @GET("transaction/expenditures/bill/{id}")
    fun getExpenditures(@Path("id") id: Int?): Call<ExpenditureTransactionArray>
}