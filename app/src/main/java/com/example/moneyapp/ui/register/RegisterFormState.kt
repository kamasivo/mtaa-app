package com.example.moneyapp.ui.register

data class RegisterFormState(val usernameError: Int? = null,
                          val passwordError: Int? = null,
                          val emailError: Int? = null,
                          val isDataValid: Boolean = false)