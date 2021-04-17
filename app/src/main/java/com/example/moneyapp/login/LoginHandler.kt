package com.example.moneyapp.login

import android.util.Log
import com.example.moneyapp.api.models.User
import com.example.moneyapp.api.services.PostUserService

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginHandler() {
    fun login(username: String, password: String): String {
        // handle sign up
        val apiService = PostUserService()

        // tu zmenit zo static na dynamicke podla inputov ...username a password
        val userInfo = User(
            fullName = "Alex",
            emailAddress = "alex@gmail.com",
            password = "user"
        )

        apiService.addUser(userInfo) {
            if (it != null) {
                Log.d("LoginRepository", it)
                if (it == "OK") {
                    // successfully created new user
                } else {
                    // nepodarilo sa vytvorit pouzivatela
                }
            }
        }

        return "OK"
    }
}