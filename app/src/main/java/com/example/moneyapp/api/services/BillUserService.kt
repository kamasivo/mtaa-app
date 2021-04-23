package com.example.moneyapp.api.services

import android.util.Log
import com.example.moneyapp.api.ServiceBuilder
import com.example.moneyapp.api.interfaces.GetBillInterface
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BillUserService {

    fun getbill(onResult: (String?) -> Unit){
        Log.d("BillUserService", "Posielam request")
        val service_builder = ServiceBuilder()
        val retrofit = service_builder.buildService(GetBillInterface::class.java)
        retrofit.getBills().enqueue(
                object : Callback<JsonObject> {
                    override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                        Log.d("BillUserService", "failed to response")
                        Log.d("BillUserService", t.toString())
                        onResult(null)
                    }
                    override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                        Log.d("BillUserService", response.toString())
                        if(response.isSuccessful) {
                            val jsonObject = JSONObject(Gson().toJson(response.body()))
                            Log.d("BillUserService", jsonObject.toString())
                            val result = jsonObject.getString("bills")
                            Log.d("BillUserService", result)
//                            onResult("OK")
                        }
                        else {
                            Log.d("BillUserService", response.code().toString())
                            onResult(response.code().toString())
                        }
                    }
                }
        )
    }
}