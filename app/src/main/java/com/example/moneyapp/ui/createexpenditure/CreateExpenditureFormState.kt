package com.example.moneyapp.ui.createexpenditure

data class CreateExpenditureFormState(val sumError: Int? = null,
                                      val billIdError: Int? = null,
                                      val categoryIdError: Int? = null,
                                      val isDataValid: Boolean = false)