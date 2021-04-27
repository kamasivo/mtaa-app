package com.example.moneyapp.ui.add_expenditure

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moneyapp.api.models.NewExpenditure
import com.example.moneyapp.api.services.TransactionService

class CreateExpenditureViewModel() : ViewModel() {
    private val _createExpenditureForm = MutableLiveData<CreateExpenditureFormState>()
    val createExpenditureFormState: LiveData<CreateExpenditureFormState> = _createExpenditureForm

    private val _createExpenditureResult = MutableLiveData<CreateExpenditureResult>()
    val createExpenditureResult: LiveData<CreateExpenditureResult> = _createExpenditureResult

    fun createExpenditure(sum: Int, billId: Int, categoryId: Int) {
        Log.d("ExpenditureViewModel", "createExpenditure initiated");
        val apiService = TransactionService()

        val expenditureInfo = NewExpenditure(
                sum = sum,
                billId = billId,
                categoryId = categoryId
        )
        apiService.addExpenditure(expenditureInfo) {
            if (it != null) {
                Log.d("ExpenditureViewModel", it)
                if (it == "OK") {
                    _createExpenditureResult.value = CreateExpenditureResult(success = true)
                } else {
                    _createExpenditureResult.value = CreateExpenditureResult(error = it)
                }
            }
        }
    }

    fun createExpenditureDataChanged(sum: Int) {
        if (!isNameValid(sum)) {
            _createExpenditureForm.value = CreateExpenditureFormState(sumError = "Invalid sum")
        } else {
            _createExpenditureForm.value = CreateExpenditureFormState(isDataValid = true)
        }
    }


    private fun isNameValid(expenditureSum: Int): Boolean {
        return expenditureSum > 0
    }
}

