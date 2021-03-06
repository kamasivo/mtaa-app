package com.example.moneyapp.ui.new_bill

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moneyapp.R
import com.example.moneyapp.api.models.NewBill
import com.example.moneyapp.api.services.BillService
import com.example.moneyapp.database.getDatabase
import com.example.moneyapp.repository.BillRepository

class NewBillViewModel() : ViewModel() {
    private val billRepository = BillRepository(getDatabase())
    private val _newBillForm = MutableLiveData<NewBillFormState>()
    val newBillFormState: LiveData<NewBillFormState> = _newBillForm

    private val _newBillResult = MutableLiveData<NewBillResult>()
    val newBillResult: LiveData<NewBillResult> = _newBillResult

    fun newBill(name: String, incomePercents: Int, description: String, sum: Int) {
        Log.d("NewBillViewModel", "newBill initiated");
        val apiService = BillService()

        val billInfo = NewBill(
            name = name,
            incomePercents = incomePercents,
            description = description,
            sum = sum
        )
        billRepository.inserBillToRepository(billInfo)
        apiService.addBill(billInfo) {
            if (it != null) {
                Log.d("NewBillViewModel", it)
                if (it == "OK") {
                    _newBillResult.value = NewBillResult(success = true)
                } else {
                    _newBillResult.value = NewBillResult(error = it)
                }
            }
        }
    }

    fun newBillDataChanged(name: String) {
        if (!isNameValid(name)) {
            _newBillForm.value = NewBillFormState(nameError = R.string.invalid_name)
        } else {
            _newBillForm.value = NewBillFormState(isDataValid = true)
        }
    }


    private fun isNameValid(username: String): Boolean {
        return username.length > 1
    }
}
