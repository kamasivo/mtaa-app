package com.example.moneyapp.api.interfaces

import com.example.moneyapp.api.models.NewCategory
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface PostCategoryInterface {
    @Headers("Content-Type: application/json")
    @POST("category/expenditure")
    fun addCategory(@Body userData: NewCategory): Call<JsonObject>
}