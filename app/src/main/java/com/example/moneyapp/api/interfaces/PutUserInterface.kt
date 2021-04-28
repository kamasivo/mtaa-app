package com.example.moneyapp.api.interfaces

import com.example.moneyapp.api.models.EditUser
import com.example.moneyapp.api.models.NewBill
import com.example.moneyapp.api.models.UpdateBill
import com.example.moneyapp.api.models.UserProfile
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.PUT

interface PutUserInterface {
    @Headers("Content-Type: application/json")
    @PUT("user/profile")
    fun updateUser(@Body userData: EditUser): Call<JsonObject>
}