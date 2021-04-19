package com.example.moneyapp.api.services

import android.util.Log
import com.example.moneyapp.api.ServiceBuilder
import com.example.moneyapp.api.interfaces.LoginUserInterface
import com.example.moneyapp.api.models.UserLogin
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginUserService {

    fun loginUser(userData: UserLogin, onResult: (String?) -> Unit){
        Log.d("LoginUserService", "Posielam request")
        val retrofit = ServiceBuilder.buildService(LoginUserInterface::class.java)
        retrofit.loginUser(userData).enqueue(
                object : Callback<JsonObject> {
                    override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                        Log.d("LoginUserService", "failed to response")
                        Log.d("LoginUserService", t.toString())
                        onResult(null)
                    }

                    override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                        Log.d("LoginUserService", response.toString())
                        if(response.isSuccessful) {
                            val jsonObject = JSONObject(Gson().toJson(response.body()))
                            Log.d("LoginUserService", jsonObject.toString())
                            val id = jsonObject.getString("id")
                            Log.d("LoginUserService", id)
                            onResult("OK")
                        }
                        else {
                            Log.d("LoginUserService", "404 response")
                            onResult("404")
                        }
                    }
                }
        )
    }
}