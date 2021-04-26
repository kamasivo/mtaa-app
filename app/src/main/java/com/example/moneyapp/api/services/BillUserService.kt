package com.example.moneyapp.api.services

import android.util.Log
import com.example.moneyapp.api.ServiceBuilder
import com.example.moneyapp.api.interfaces.GetBillInterface
import com.example.moneyapp.api.models.BillsArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BillUserService {

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
}