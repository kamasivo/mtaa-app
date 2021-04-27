package com.example.moneyapp.ui.createincome

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moneyapp.api.models.Bill
import com.example.moneyapp.api.models.NewIncome
import com.example.moneyapp.api.services.BillService
import com.example.moneyapp.api.services.PostIncomeService

class CreateIncomeViewModel() : ViewModel() {
    private val _createIncomeForm = MutableLiveData<CreateIncomeFormState>()
    val createIncomeFormState: LiveData<CreateIncomeFormState> = _createIncomeForm

    private val _createIncomeResult = MutableLiveData<CreateIncomeResult>()
    val createIncomeResult: LiveData<CreateIncomeResult> = _createIncomeResult

    val listOfBills: MutableLiveData<List<Bill>> by lazy {
        MutableLiveData<List<Bill>>()
    }

    fun createIncome(sum: Int, billId: Int, categoryId: Int) {
        Log.d("CreateIncomeViewModel", "createIncome initiated");
        val apiService = PostIncomeService()

        val incomeInfo = NewIncome(
            sum = sum,
            billId = billId,
            categoryId = categoryId
        )
        apiService.addIncome(incomeInfo) {
            if (it != null) {
                Log.d("CreateIncomeViewModel", it)
                if (it == "OK") {
                    _createIncomeResult.value = CreateIncomeResult(success = true)
                } else {
                    _createIncomeResult.value = CreateIncomeResult(error = it)
                }
            }
        }
    }

    fun createIncomeDataChanged(sum: Int) {
        if (!isNameValid(sum)) {
            _createIncomeForm.value = CreateIncomeFormState(sumError = "Invalid sum")
        } else {
            _createIncomeForm.value = CreateIncomeFormState(isDataValid = true)
        }
    }

    fun loadBills() {
        val apiService = BillService()


        apiService.getbill {
            if (it != null) {
                Log.d("CreateIncomeView", "bills loaded")
                listOfBills.value = it.bills
            }
        }
    }


    private fun isNameValid(incomeSum: Int): Boolean {
        return incomeSum > 0
    }
}
