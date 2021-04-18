package com.example.moneyapp.login

import android.util.Log

class RegisterHandler {
    fun register(username: String, password: String, email: String): String {
        Log.d("RegisterHandler", "handler is initiated");
        return "OK";
    }
}