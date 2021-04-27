package com.example.moneyapp.ui.category

data class NewCategoryFormState(val nameError: Int? = null,
                                val billIdError: Int? = null,
                                val isDataValid: Boolean = false)