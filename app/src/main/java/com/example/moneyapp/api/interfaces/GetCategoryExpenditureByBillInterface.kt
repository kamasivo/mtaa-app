package com.example.moneyapp.api.interfaces

import com.example.moneyapp.api.models.CategoryArray
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface GetCategoryExpenditureByBillInterface {
    @Headers("Content-Type: application/json")
    @GET("category/expenditure/{id}")
    fun getExpenditureCategoriesByBill(@Path("id") id: Int?): Call<CategoryArray>
}