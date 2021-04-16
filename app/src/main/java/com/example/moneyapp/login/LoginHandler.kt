package com.example.moneyapp.login

import android.util.Log
import com.example.moneyapp.api.models.User
import com.example.moneyapp.api.services.PostUserService

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginHandler() {
    // in-memory cache of the loggedInUser object
//    var user: LoggedInUser? = null
//        private set

//    val isLoggedIn: Boolean
//        get() = user != null

//    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
//        user = null
//    }

//    fun logout() {
//        user = null
//        dataSource.logout()
//    }

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
                if(it == "OK") {
                    // successfully created new user
                }
                else {
                    // nepodarilo sa vytvorit pouzivatela
                }
            }
        }

        return "OK"
//        val result = dataSource.login(username, password)

//        if (result is Result.Success) {
//            setLoggedInUser(result.data)
//        }
//        return result
    }

//    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
//        this.user = loggedInUser
//         If user credentials will be cached in local storage, it is recommended it be encrypted
//         @see https://developer.android.com/training/articles/keystore
//    }
}