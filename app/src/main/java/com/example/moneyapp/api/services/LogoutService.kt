package com.example.moneyapp.api.services

import android.util.Log
import com.example.moneyapp.api.ServiceBuilder
import com.example.moneyapp.api.interfaces.LogoutUserInterface
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LogoutService {

    fun logoutUser(onResult: (String?) -> Unit){
        Log.d("LogoutService", "Loging out")
        val service_builder = ServiceBuilder()
        val retrofit = service_builder.buildService(LogoutUserInterface::class.java)
        retrofit.loginUser().enqueue(
            object : Callback<JsonObject> {
                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Log.d("LogoutService", "failed to response")
                    Log.d("LogoutService", t.toString())
                    onResult(null)
                }

                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    Log.d("LogoutService", response.toString())
                    if(response.isSuccessful) {
                        val jsonObject = JSONObject(Gson().toJson(response.body()))
                        Log.d("LogoutService", jsonObject.toString())
                        val result = jsonObject.getString("result")
                        Log.d("LogoutService", result)
                        onResult("OK")
                    }
                    else {
                        Log.d("LogoutService", response.code().toString())
                        onResult(response.code().toString())
                    }
                }
            }
        )
    }
}