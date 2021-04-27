package com.example.moneyapp.api.services

import android.util.Log
import com.example.moneyapp.api.ServiceBuilder
import com.example.moneyapp.api.interfaces.GetCategoryIncomesInterface
import com.example.moneyapp.api.interfaces.PostCategoryInterface
import com.example.moneyapp.api.models.CategoryArray
import com.example.moneyapp.api.models.NewCategory
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryService {
    fun addCategory(userData: NewCategory, onResult: (String?) -> Unit){
        Log.d("PostCategoryService", "Posielam request")
        val service_builder = ServiceBuilder()
        val retrofit = service_builder.buildService(PostCategoryInterface::class.java)
        retrofit.addCategory(userData).enqueue(
            object : Callback<JsonObject> {
                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Log.d("PostCategoryService", "failed to response")
                    Log.d("PostCategoryService", t.toString())
                    onResult(null)
                }

                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    Log.d("PostCategoryService", response.toString())
                    if(response.isSuccessful) {
                        val jsonObject = JSONObject(Gson().toJson(response.body()))
                        Log.d("PostCategoryService", jsonObject.toString())
                        val id = jsonObject.getString("result")
                        Log.d("PostCategoryService", id)
                        if(id != null) {
                            onResult("OK")
                        }
                    }
                    else {
                        Log.d("PostCategoryService", response.message().toString())
                        onResult(response.message().toString())
                    }
                }
            }
        )
    }


    fun getIncomeCategories(onResult: (CategoryArray?) -> Unit){
        Log.d("GetIncCategoryService", "Posielam request")
        val service_builder = ServiceBuilder()
        val retrofit = service_builder.buildService(GetCategoryIncomesInterface::class.java)
        retrofit.getIncomeCategories().enqueue(
            object : Callback<CategoryArray> {
                override fun onFailure(call: Call<CategoryArray>, t: Throwable) {
                    Log.d("GetIncCategoryService", "failed to response")
                    Log.d("GetIncCategoryService", t.toString())
                    onResult(null)
                }

                override fun onResponse(call: Call<CategoryArray>, response: Response<CategoryArray>) {
                    Log.d("GetIncCategoryService", response.toString())
                    if(response.isSuccessful) {
                        Log.d("GetIncCategoryService", response.body().toString())
                        onResult(response.body())

                    }
                    else {
                        Log.d("GetIncCategoryService", response.message().toString())
                    }
                }
            }
        )
    }
}