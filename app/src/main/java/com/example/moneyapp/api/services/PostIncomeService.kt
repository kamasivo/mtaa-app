package com.example.moneyapp.api.services

import android.util.Log
import com.example.moneyapp.api.ServiceBuilder
import com.example.moneyapp.api.interfaces.PostIncomeInterface
import com.example.moneyapp.api.models.NewIncome
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PostIncomeService {
    fun addIncome(userData: NewIncome, onResult: (String?) -> Unit){
        Log.d("PostIncomeService", "Posielam request")
        val service_builder = ServiceBuilder()
        val retrofit = service_builder.buildService(PostIncomeInterface::class.java)
        retrofit.addIncome(userData).enqueue(
                object : Callback<JsonObject> {
                    override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                        Log.d("PostIncomeService", "failed to response")
                        Log.d("PostIncomeService", t.toString())
                        onResult(null)
                    }

                    override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                        Log.d("PostIncomeService", response.toString())
                        if(response.isSuccessful) {
                            val jsonObject = JSONObject(Gson().toJson(response.body()))
                            Log.d("PostIncomeService", jsonObject.toString())
                            val id = jsonObject.getString("result")
                            Log.d("PostIncomeService", id)
                            if(id != null) {
                                onResult("OK")
                            }
                        }
                        else {
                            Log.d("PostIncomeService", response.message().toString())
                            onResult(response.message().toString())
                        }
                    }
                }
        )
    }
}