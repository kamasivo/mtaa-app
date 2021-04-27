package com.example.moneyapp.api.services

import android.util.Log
import com.example.moneyapp.api.ServiceBuilder
import com.example.moneyapp.api.interfaces.PostIncomeInterface
import com.example.moneyapp.api.interfaces.PostSplitIncomeInterface
import com.example.moneyapp.api.models.NewIncome
import com.example.moneyapp.api.models.NewSplitIncome
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PostSplitIncomeService {
    fun addSplitIncome(userData: NewSplitIncome, onResult: (String?) -> Unit){
        Log.d("PostSplitIncomeService", "Posielam request")
        val service_builder = ServiceBuilder()
        val retrofit = service_builder.buildService(PostSplitIncomeInterface::class.java)
        retrofit.addSplitIncome(userData).enqueue(
                object : Callback<JsonObject> {
                    override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                        Log.d("PostSplitIncomeService", "failed to response")
                        Log.d("PostSplitIncomeService", t.toString())
                        onResult(null)
                    }

                    override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                        Log.d("PostSplitIncomeService", response.toString())
                        if(response.isSuccessful) {
                            val jsonObject = JSONObject(Gson().toJson(response.body()))
                            Log.d("PostSplitIncomeService", jsonObject.toString())
                            val id = jsonObject.getString("result")
                            Log.d("PostSplitIncomeService", id)
                            if(id != null) {
                                onResult("OK")
                            }
                        }
                        else {
                            Log.d("PostSplitIncomeService", response.message().toString())
                            onResult(response.message().toString())
                        }
                    }
                }
        )
    }
}