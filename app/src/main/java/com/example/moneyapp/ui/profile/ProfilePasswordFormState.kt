package com.example.moneyapp.ui.profile

data class ProfilePasswordFormState(val passwordError: Int? = null,
                                    val newPasswordError: Int? = null,
                                    val repeatPasswordError: Int? = null,
                                    val isDataValid: Boolean = false)