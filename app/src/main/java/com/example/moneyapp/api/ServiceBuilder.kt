package com.example.moneyapp.api

import android.content.Context
import com.example.moneyapp.GlobalApplication
import com.example.moneyapp.api.cookie.AddCookiesInterceptor
import com.example.moneyapp.api.cookie.RecievedCookiesInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


// here is setup od url where is api server
class ServiceBuilder {
    var context: Context? = GlobalApplication.appContext
    private val client = OkHttpClient.Builder().addInterceptor(AddCookiesInterceptor(context)).addInterceptor(RecievedCookiesInterceptor(context)).build()
//    private val client = OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder()
            .baseUrl("https://gamma-bank.herokuapp.com/") // change this IP for testing by your actual machine IP
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun <T> buildService(service: Class<T>): T{
        return retrofit.create(service)
    }
}