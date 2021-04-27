package com.example.moneyapp.ui.new_bill

data class NewBillFormState(val nameError: Int? = null,
                            val incomePercentsError: Int? = null,
                            val descriptionError: Int? = null,
                            val sumError: Int? = null,
                            val isDataValid: Boolean = false)