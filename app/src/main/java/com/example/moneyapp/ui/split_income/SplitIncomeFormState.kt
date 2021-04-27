package com.example.moneyapp.ui.split_income

data class SplitIncomeFormState(val sumError: String? = null,
                                val categoryIdError: Int? = null,
                                val isDataValid: Boolean = false)