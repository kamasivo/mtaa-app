package com.example.moneyapp.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
//import com.example.moneyapp.login.LoginDataSource
import com.example.moneyapp.login.LoginHandler

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class LoginViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                    loginHandler = LoginHandler()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}