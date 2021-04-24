package com.example.moneyapp.ui.newbill

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moneyapp.R
import com.example.moneyapp.api.models.Bill
import com.example.moneyapp.api.services.PostBillService

class NewBillViewModel() : ViewModel() {
    private val _newBillForm = MutableLiveData<NewBillFormState>()
    val newBillFormState: LiveData<NewBillFormState> = _newBillForm

    private val _newBillResult = MutableLiveData<NewBillResult>()
    val newBillResult: LiveData<NewBillResult> = _newBillResult

    fun newBill(name: String, incomePercents: Int, description: String, sum: Int) {
        Log.d("NewBillViewModel", "newBill initiated");
        val apiService = PostBillService()

        val billInfo = Bill(
                name = name,
                incomePercents = 123,
                description = description,
                sum = 123
        )
        apiService.addBill(billInfo) {
            if (it != null) {
                Log.d("NewBillViewModel", it)
                if (it == "OK") {
                    _newBillResult.value = NewBillResult(success = true)
                } else {
                    _newBillResult.value = NewBillResult(error = R.string.login_failed)
                }
            }
        }
    }

    fun newBillDataChanged(name: String, incomePercents: Int, description: String, sum: Int) {
        if (!isNameValid(name)) {
            _newBillForm.value = NewBillFormState(nameError = R.string.invalid_name)
        } else if (!isIncomePercentsValid(incomePercents)) {
            _newBillForm.value = NewBillFormState(incomePercentsError = R.string.invalid_incomePercents)
        } else if (!isSumValid(sum)){
            _newBillForm.value = NewBillFormState(sumError = R.string.invalid_sum)
        } else {
            _newBillForm.value = NewBillFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isSumValid(sum: Int): Boolean {
        return sum != null
    }

    // A placeholder password validation check
    private fun isIncomePercentsValid(incomePercents: Int): Boolean {
        return incomePercents != null
    }
    private fun isNameValid(username: String): Boolean {
        return username.length > 2
    }
}