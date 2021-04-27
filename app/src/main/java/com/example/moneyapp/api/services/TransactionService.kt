package com.example.moneyapp.api.services

import android.util.Log
import com.example.moneyapp.api.ServiceBuilder
import com.example.moneyapp.api.interfaces.PostExpenditureInterface
import com.example.moneyapp.api.interfaces.PostIncomeInterface
import com.example.moneyapp.api.interfaces.PostSplitIncomeInterface
import com.example.moneyapp.api.models.NewExpenditure
import com.example.moneyapp.api.models.NewIncome
import com.example.moneyapp.api.models.NewSplitIncome
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransactionService {
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

    fun addIncome(income: NewIncome, onResult: (String?) -> Unit){
        Log.d("PostIncomeService", "Posielam request")
        val service_builder = ServiceBuilder()
        val retrofit = service_builder.buildService(PostIncomeInterface::class.java)
        retrofit.addIncome(income).enqueue(
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
                        if(id == "success") {
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