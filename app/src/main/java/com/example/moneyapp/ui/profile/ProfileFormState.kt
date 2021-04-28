package com.example.moneyapp.ui.profile

data class ProfileFormState(val nameError: Int? = null,
                            val emailError: Int? = null,
                            val isDataValid: Boolean = false)