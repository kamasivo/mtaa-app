package com.example.moneyapp.api.services

import android.util.Log
import com.example.moneyapp.api.ServiceBuilder
import com.example.moneyapp.api.interfaces.PostUserInterface
import com.example.moneyapp.api.models.User
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PostUserService {
    fun addUser(userData: User, onResult: (String?) -> Unit) {
      Log.d("PostUserService", "Posielam request")
        val retrofit = ServiceBuilder.buildService(PostUserInterface::class.java)
        retrofit.addUser(userData).enqueue(
            object : Callback<JsonObject> {
                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Log.d("PostUserService", "failed to response")
                    Log.d("PostUserService", t.toString())
                    onResult(null)
                }

                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    Log.d("PostUserService", response.toString())
                    if(response.isSuccessful) {
                        val jsonObject = JSONObject(Gson().toJson(response.body()))
                        Log.d("PostUserService", jsonObject.toString())
                        val id = jsonObject.getString("id")
                        Log.d("PostUserService", id)
                        if(id != null) {
                            onResult("OK")
                        }
                    }
                    else {
                        Log.d("PostUserService", "404 response")
                        onResult("404")
                    }
                }
            }
        )
    }
}