package com.example.moneyapp.api.interfaces

import com.example.moneyapp.api.models.CategoryArray
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface GetCategoryIncomesInterface {
    @Headers("Content-Type: application/json")
    @GET("category/income")
    fun getIncomeCategories(): Call<CategoryArray>
}