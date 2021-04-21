package com.example.moneyapp.ui.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moneyapp.R
import com.example.moneyapp.api.models.User
import com.example.moneyapp.api.services.PostUserService

class RegisterViewModel() : ViewModel() {
    private val _registerForm = MutableLiveData<RegisterFormState>()
    val registerFormState: LiveData<RegisterFormState> = _registerForm

    private val _registerResult = MutableLiveData<RegisterResult>()
    val registerResult: LiveData<RegisterResult> = _registerResult

    fun register(username: String, password: String, email: String) {
        Log.d("RegisterViewModel", "register initiated");
        val apiService = PostUserService()

        val userInfo = User(
                fullName = username,
                emailAddress = email,
                password = password
        )
        apiService.addUser(userInfo) {
            if (it != null) {
                Log.d("RegisterViewModel", it)
                if (it == "OK") {
                    _registerResult.value = RegisterResult(success = true)
                } else {
                    _registerResult.value = RegisterResult(error = R.string.login_failed)
                }
            }
        }
    }

    fun registerDataChanged(username: String, password: String, email: String) {
        if (!isEmailValid(email)) {
            _registerForm.value = RegisterFormState(emailError = R.string.invalid_email)
        } else if (!isPasswordValid(password)) {
            _registerForm.value = RegisterFormState(passwordError = R.string.invalid_password)
        } else if (!isUserNameValid(username)){
            _registerForm.value = RegisterFormState(usernameError = R.string.invalid_username)
        } else {
            _registerForm.value = RegisterFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isEmailValid(email: String): Boolean {
        if(email.contains('@') && email.length > 4) {
            return true
        }
        return false
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 4
    }
    private fun isUserNameValid(username: String): Boolean {
        return username.length > 2
    }
}