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

class UserService {
    fun loginUser(userData: UserLogin, onResult: (String?) -> Unit){
        Log.d("LoginUserService", "Posielam request")
        val service_builder = ServiceBuilder()
        val retrofit = service_builder.buildService(LoginUserInterface::class.java)
        retrofit.loginUser(userData).enqueue(
            object : Callback<JsonObject> {
                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Log.d("LoginUserService", "failed to response")
                    Log.d("LoginUserService", t.toString())
                    onResult(null)
                }
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    Log.d("LoginUserService", response.toString())
                    if(response.isSuccessful) {
                        val jsonObject = JSONObject(Gson().toJson(response.body()))
                        Log.d("LoginUserService", jsonObject.toString())
                        val result = jsonObject.getString("result")
                        Log.d("LoginUserService", result)
                        onResult("OK")
                    }
                    else {
                        Log.d("LoginUserService", response.code().toString())
                        onResult(response.code().toString())
                    }
                }
            }
        )
    }

    fun logoutUser(onResult: (String?) -> Unit){
        Log.d("LogoutService", "Loging out")
        val service_builder = ServiceBuilder()
        val retrofit = service_builder.buildService(LogoutUserInterface::class.java)
        retrofit.loginUser().enqueue(
            object : Callback<JsonObject> {
                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Log.d("LogoutService", "failed to response")
                    Log.d("LogoutService", t.toString())
                    onResult(null)
                }

                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    Log.d("LogoutService", response.toString())
                    if(response.isSuccessful) {
                        val jsonObject = JSONObject(Gson().toJson(response.body()))
                        Log.d("LogoutService", jsonObject.toString())
                        val result = jsonObject.getString("result")
                        Log.d("LogoutService", result)
                        onResult("OK")
                    }
                    else {
                        Log.d("LogoutService", response.code().toString())
                        onResult(response.code().toString())
                    }
                }
            }
        )
    }
    fun addUser(userData: User, onResult: (String?) -> Unit){
        Log.d("PostUserService", "Posielam request")
        val service_builder = ServiceBuilder()
        val retrofit = service_builder.buildService(PostUserInterface::class.java)
        retrofit.addUser(userData).enqueue(
            object : Callback<JsonObject> {
                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Log.d("PostUserService", "failed to response")
                    Log.d("PostUserService", t.toString())
                    onResult(null)
                }
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    Log.d("PostUserService", response.toString())
                    if(response.isSuccessful) {
                        val jsonObject = JSONObject(Gson().toJson(response.body()))
                        Log.d("PostUserService", jsonObject.toString())
                        val id = jsonObject.getString("result")
                        Log.d("PostUserService", id)
                        if(id != null) {
                            onResult("OK")
                        }
                    }
                    else {
                        Log.d("PostUserService", response.code().toString())
                        onResult(response.code().toString())
                    }
                }
            }
        )
    }
    fun getUser(onResult: (UserProfile?) -> Unit){
        Log.d("UserService", "Posielam request")
        val serviceBuilder = ServiceBuilder()
        val retrofit = serviceBuilder.buildService(GetUserInterface::class.java)
        retrofit.getUser().enqueue(
                object : Callback<UserResponse> {
                    override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                        Log.d("UserService", "failed to response")
                        Log.d("UserService", t.toString())
                        onResult(null)
                    }

                    override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                        Log.d("UserService", response.toString())
                        if(response.isSuccessful) {
                            Log.d("UserService", response.body().toString())
                            onResult(response.body()?.user)
                        }
                        else {
                            Log.d("UserService", response.message().toString())
                        }
                    }
                }
        )
    }
    fun updateProfile(userData: UserProfile, onResult: (String?) -> Unit){
        Log.d("PutUserService", "Posielam request")
        val service_builder = ServiceBuilder()
        val retrofit = service_builder.buildService(PutUserInterface::class.java)
        retrofit.updateUser(userData).enqueue(
                object : Callback<JsonObject> {
                    override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                        Log.d("PutUserService", "failed to response")
                        Log.d("PutUserService", t.toString())
                        onResult(null)
                    }
                    override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                        Log.d("PutUserService", response.toString())
                        if(response.isSuccessful) {
                            val jsonObject = JSONObject(Gson().toJson(response.body()))
                            Log.d("PutUserService", jsonObject.toString())
                            val id = jsonObject.getString("result")
                            Log.d("PutUserService", id)
                            if(id != null) {
                                onResult("OK")
                            }
                        }
                        else {
                            Log.d("PutUserService", response.message().toString())
                            onResult(response.message().toString())
                        }
                    }
                }
        )
    }

    fun updatePassword(userData: Password, onResult: (String?) -> Unit){
        Log.d("PutUserService", "Posielam request")
        val service_builder = ServiceBuilder()
        val retrofit = service_builder.buildService(PutPasswordInterface::class.java)
        retrofit.updatePassword(userData).enqueue(
                object : Callback<JsonObject> {
                    override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                        Log.d("PutPasswordService", "failed to response")
                        Log.d("PutPasswordService", t.toString())
                        onResult(null)
                    }
                    override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                        Log.d("PutPasswordService", response.toString())
                        if(response.isSuccessful) {
                            val jsonObject = JSONObject(Gson().toJson(response.body()))
                            Log.d("PutPasswordService", jsonObject.toString())
                            val id = jsonObject.getString("result")
                            Log.d("PutPasswordService", id)
                            if(id != null) {
                                onResult("OK")
                            }
                        }
                        else {
                            Log.d("PutPasswordService", response.message().toString())
                            onResult(response.message().toString())
                        }
                    }
                }
        )
    }
}