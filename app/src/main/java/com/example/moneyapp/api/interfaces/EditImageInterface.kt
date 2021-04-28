package com.example.moneyapp.api.interfaces

import com.example.moneyapp.api.models.NewImage
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.PUT

    interface EditImageInterface {
        @Headers("Content-Type: application/json")
        @PUT("image")
        fun image(@Body newImage: NewImage): Call<JsonObject>
    }
