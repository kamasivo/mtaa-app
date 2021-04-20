package com.example.moneyapp.login

import android.util.Log
import com.example.moneyapp.api.models.User
import com.example.moneyapp.api.services.PostUserService

class RegisterHandler {
    fun register(username: String, password: String, email: String): String {
        Log.d("RegisterHandler", "handler is initiated");
        var result = "FAIL"

        // handle sign up
        val apiService = PostUserService()

        val userInfo = User(
            fullName = username,
            emailAddress = email,
            password = password
        )
        Log.d("RegisterHandler", userInfo.toString());
        apiService.addUser(userInfo) {
            if (it != null) {
                Log.d("RegisterRepository", it)
                if (it == "OK") {
                    result = "OK"
                } else {
                    result = "CONFLICT"
                }
            }
        }
        Log.d("RegisterRepository", result)
        return result
    }
}