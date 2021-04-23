package com.example.moneyapp.ui.newbill

data class NewBillFormState(val usernameError: Int? = null,
                            val passwordError: Int? = null,
                            val emailError: Int? = null,
                            val isDataValid: Boolean = false)