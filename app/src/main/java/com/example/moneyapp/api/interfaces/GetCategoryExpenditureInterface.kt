package com.example.moneyapp.api.interfaces

import com.example.moneyapp.api.models.CategoryArray
import com.example.moneyapp.api.models.ExpenditureCategoryArray
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface GetCategoryExpenditureInterface {
    @Headers("Content-Type: application/json")
    @GET("category/expenditure")
    fun getExpenditureCategories(): Call<ExpenditureCategoryArray>
}