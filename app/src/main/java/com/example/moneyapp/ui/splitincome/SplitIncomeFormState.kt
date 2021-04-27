package com.example.moneyapp.ui.createincome

data class SplitIncomeFormState(val sumError: String = null,
                                val categoryIdError: Int? = null,
                                val isDataValid: Boolean = false)