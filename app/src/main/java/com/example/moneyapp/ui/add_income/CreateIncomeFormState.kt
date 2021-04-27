package com.example.moneyapp.ui.add_income

data class CreateIncomeFormState(val sumError: String? = null,
                                 val billIdError: Int? = null,
                                 val categoryIdError: Int? = null,
                                 val isDataValid: Boolean = false)