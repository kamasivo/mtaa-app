package com.example.moneyapp.api.services

import android.util.Log
import com.example.moneyapp.api.ServiceBuilder
import com.example.moneyapp.api.interfaces.PostUserInterface
import com.example.moneyapp.api.models.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostUserService {
    fun addUser(userData: User, onResult: (String?) -> Unit){
      Log.d("RestApiService", "Posielam request")
        val retrofit = ServiceBuilder.buildService(PostUserInterface::class.java)
        retrofit.addUser(userData).enqueue(
            object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse( call: Call<String>, response: Response<String>) {
                    val res = response.toString()
                    Log.d("RestApIService", res)
                    onResult(res)
                }
            }
        )
    }
}