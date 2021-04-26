package com.example.moneyapp.api.services

import android.util.Log
import com.example.moneyapp.api.ServiceBuilder
import com.example.moneyapp.api.interfaces.PostExpenditureInterface
import com.example.moneyapp.api.models.NewExpenditure
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PostExpenditureService {
    fun addExpenditure(userData: NewExpenditure, onResult: (String?) -> Unit){
        Log.d("PostExpenditureService", "Posielam request")
        val service_builder = ServiceBuilder()
        val retrofit = service_builder.buildService(PostExpenditureInterface::class.java)
        retrofit.addExpenditure(userData).enqueue(
                object : Callback<JsonObject> {
                    override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                        Log.d("PostExpenditureService", "failed to response")
                        Log.d("PostExpenditureService", t.toString())
                        onResult(null)
                    }

                    override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                        Log.d("PostExpenditureService", response.toString())
                        if(response.isSuccessful) {
                            val jsonObject = JSONObject(Gson().toJson(response.body()))
                            Log.d("PostExpenditureService", jsonObject.toString())
                            val id = jsonObject.getString("result")
                            Log.d("PostExpenditureService", id)
                            if(id != null) {
                                onResult("OK")
                            }
                        }
                        else {
                            Log.d("PostExpenditureService", response.message().toString())
                            onResult(response.message().toString())
                        }
                    }
                }
        )
    }
}