package com.example.moneyapp.api.services

import android.util.Log
import com.example.moneyapp.api.ServiceBuilder
import com.example.moneyapp.api.interfaces.*
import com.example.moneyapp.api.models.*
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
    fun getIncome(billId: Int, onResult: (TransactionArray?) -> Unit){
        Log.d("TransactionUserServiceInc", "Posielam request")
        val service_builder = ServiceBuilder()
        val retrofit = service_builder.buildService(GetIncomeInterface::class.java)
        retrofit.getIncomes(billId).enqueue(
                object : Callback<TransactionArray> {
                    override fun onFailure(call: Call<TransactionArray>, t: Throwable) {
                        Log.d("TransactionUserServiceInc", "failed to response")
                        Log.d("TransactionUserServiceInc", t.toString())
                        onResult(null)
                    }
                    override fun onResponse(call: Call<TransactionArray>, response: Response<TransactionArray>) {
                        Log.d("TransactionUserServiceInc", response.toString())
                        if(response.isSuccessful) {
                            Log.d("TransactionUserServiceInc", response.body().toString())
                            onResult(response.body())
                        }
                        else {
                            Log.d("TransactionUserServiceInc", response.code().toString())
                        }
                    }
                }
        )
    }
    fun getExpenditure(billId: Int, onResult: (ExpenditureTransactionArray?) -> Unit){
        Log.d("TransactionUserServiceExp", "Posielam request")
        Log.d("TransactionUserServiceExp", billId.toString())
        val service_builder = ServiceBuilder()
        val retrofit = service_builder.buildService(GetExpenditureInterface::class.java)
        retrofit.getExpenditures(billId).enqueue(
                object : Callback<ExpenditureTransactionArray> {
                    override fun onFailure(call: Call<ExpenditureTransactionArray>, t: Throwable) {
                        Log.d("TransactionUserServiceExp", "failed to response")
                        Log.d("TransactionUserServiceExp", t.toString())
                        onResult(null)
                    }
                    override fun onResponse(call: Call<ExpenditureTransactionArray>, response: Response<ExpenditureTransactionArray>) {
                        Log.d("TransactionUserServiceExp", response.toString())
                        if(response.isSuccessful) {
                            Log.d("TransactionUserServiceExp", response.body().toString())
                            onResult(response.body())
                        }
                        else {
                            Log.d("TransactionUserServiceExp", response.message().toString())
                        }
                    }
                }
        )
    }
    fun updateTransaction(userData: UpdateTransaction, onResult: (String?) -> Unit){
        Log.d("PutTransactionService", "Posielam request")
        val service_builder = ServiceBuilder()
        val retrofit = service_builder.buildService(PutTransactionInterface::class.java)
        retrofit.updateTransaction(userData).enqueue(
                object : Callback<JsonObject> {
                    override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                        Log.d("PutTransactionService", "failed to response")
                        Log.d("PutTransactionService", t.toString())
                        onResult(null)
                    }
                    override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                        Log.d("PutTransactionService", response.toString())
                        if(response.isSuccessful) {
                            val jsonObject = JSONObject(Gson().toJson(response.body()))
                            Log.d("PutTransactionService", jsonObject.toString())
                            val id = jsonObject.getString("result")
                            Log.d("PutTransactionService", id)
                            onResult("OK")
                        }
                        else {
                            Log.d("PutTransactionService", response.message().toString())
                            onResult(response.message().toString())
                        }
                    }
                }
        )
    }
    fun deleteTransaction(transactionId: Int, onResult: (String?) -> Unit){
        Log.d("DeleteTransaction", "Posielam request")
        val service_builder = ServiceBuilder()
        val retrofit = service_builder.buildService(DeleteTransactionInterface::class.java)
        retrofit.deleteTransaction(transactionId).enqueue(
                object : Callback<JsonObject> {
                    override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                        Log.d("DeleteTransaction", "failed to response")
                        Log.d("DeleteTransaction", t.toString())
                        onResult(null)
                    }

                    override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                        Log.d("DeleteTransaction", response.toString())
                        if(response.isSuccessful) {
                            val jsonObject = JSONObject(Gson().toJson(response.body()))
                            Log.d("DeleteTransaction", jsonObject.toString())
                            val id = jsonObject.getString("result")
                            Log.d("DeleteTransaction", id)
                            if(id != null) {
                                onResult("OK")
                            }
                        }
                        else {
                            Log.d("DeleteTransaction", response.message().toString())
                            onResult(response.message().toString())
                        }
                    }
                }
        )
    }
}