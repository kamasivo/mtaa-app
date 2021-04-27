package com.example.moneyapp.api.services

import android.util.Log
import com.example.moneyapp.api.ServiceBuilder
import com.example.moneyapp.api.interfaces.PostTypeInterface
import com.example.moneyapp.api.models.NewType
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PostTypeService {
    fun addType(userData: NewType, onResult: (String?) -> Unit){
        Log.d("PostTypeService", "Posielam request")
        val service_builder = ServiceBuilder()
        val retrofit = service_builder.buildService(PostTypeInterface::class.java)
        retrofit.addType(userData).enqueue(
                object : Callback<JsonObject> {
                    override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                        Log.d("PostTypeService", "failed to response")
                        Log.d("PostTypeService", t.toString())
                        onResult(null)
                    }

                    override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                        Log.d("PostTypeService", response.toString())
                        if(response.isSuccessful) {
                            val jsonObject = JSONObject(Gson().toJson(response.body()))
                            Log.d("PostTypeService", jsonObject.toString())
                            val id = jsonObject.getString("result")
                            Log.d("PostTypeService", id)
                            if(id != null) {
                                onResult("OK")
                            }
                        }
                        else {
                            Log.d("PostTypeService", response.message().toString())
                            onResult(response.message().toString())
                        }
                    }
                }
        )
    }
}