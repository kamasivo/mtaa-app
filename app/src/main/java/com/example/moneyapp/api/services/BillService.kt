package com.example.moneyapp.api.services

import android.util.Log
import com.example.moneyapp.api.ServiceBuilder
import com.example.moneyapp.api.interfaces.GetBillByIdInterface
import com.example.moneyapp.api.interfaces.GetBillInterface
import com.example.moneyapp.api.interfaces.PostBillInterface
import com.example.moneyapp.api.models.Bill
import com.example.moneyapp.api.models.BillResponse
import com.example.moneyapp.api.models.BillsArray
import com.example.moneyapp.api.models.NewBill
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BillService {
    fun getbill(onResult: (BillsArray?) -> Unit){
        Log.d("BillUserService", "Posielam request")
        val service_builder = ServiceBuilder()
        val retrofit = service_builder.buildService(GetBillInterface::class.java)
        retrofit.getBills().enqueue(
            object : Callback<BillsArray> {
                override fun onFailure(call: Call<BillsArray>, t: Throwable) {
                    Log.d("BillUserService", "failed to response")
                    Log.d("BillUserService", t.toString())
                    onResult(null)
                }
                override fun onResponse(call: Call<BillsArray>, response: Response<BillsArray>) {
                    Log.d("BillUserService", response.toString())
                    if(response.isSuccessful) {
//                            Log.d("BillUserService", response.body().toString())
                        val bills = response.body()
//                            Log.d("BillUserService", bills.toString())
                        onResult(bills)
                    }
                    else {
                        Log.d("BillUserService", response.code().toString())
                    }
                }
            }
        )
    }

    fun addBill(userData: NewBill, onResult: (String?) -> Unit){
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
                        Log.d("PostBillService", response.message().toString())
                        onResult(response.message().toString())
                    }
                }
            }
        )
    }

    fun getBillById(billId: Int, onResult: (Bill?) -> Unit){
        Log.d("BillService", "Posielam request")
        val serviceBuilder = ServiceBuilder()
        val retrofit = serviceBuilder.buildService(GetBillByIdInterface::class.java)
        retrofit.getBill(billId).enqueue(
            object : Callback<BillResponse> {
                override fun onFailure(call: Call<BillResponse>, t: Throwable) {
                    Log.d("BillService", "failed to response")
                    Log.d("BillService", t.toString())
                    onResult(null)
                }

                override fun onResponse(call: Call<BillResponse>, response: Response<BillResponse>) {
                    Log.d("BillService", response.toString())
                    if(response.isSuccessful) {
                        Log.d("BillService", response.body().toString())
                            onResult(response.body()?.bill)
                    }
                    else {
                        Log.d("BillService", response.message().toString())
                    }
                }
            }
        )
    }
}