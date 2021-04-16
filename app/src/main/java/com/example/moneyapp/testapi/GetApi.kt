package com.example.moneyapp.testapi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface GetApi {

    @Headers("Content-Type: application/json")
    @GET("posts")
    fun getPosts(): Call<List<Post>>
}