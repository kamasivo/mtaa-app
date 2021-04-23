package com.example.moneyapp.api.services

import android.util.Log
import com.example.moneyapp.api.ServiceBuilder
import com.example.moneyapp.api.interfaces.PostBillInterface
import com.example.moneyapp.api.models.Bill
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PostBillService {
    fun addBill(userData: Bill, onResult: (String?) -> Unit){
      Log.d("PostBillService", "Posielam request")
        val service_builder = ServiceBuilder()
        val retrofit = service_builder.buildService(PostBillInterface::class.java)
        retrofit.addBill(userData).enqueue(
            object : Callback<JsonObject> {
                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Log.d("PostBillService", "failed to response")
                    Log.d("PostBillService", t.toString())
                    onResult(null)
                }

                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    Log.d("PostBillService", response.toString())
                    if(response.isSuccessful) {
                        val jsonObject = JSONObject(Gson().toJson(response.body()))
                        Log.d("PostBillService", jsonObject.toString())
                        val id = jsonObject.getString("result")
                        Log.d("PostBillService", id)
                        if(id != null) {
                            onResult("OK")
                        }
                    }
                    else {
                        Log.d("PostBillService", response.code().toString())
                        onResult(response.code().toString())
                    }
                }
            }
        )
    }
}