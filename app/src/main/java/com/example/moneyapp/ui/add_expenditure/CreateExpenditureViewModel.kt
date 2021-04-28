package com.example.moneyapp.ui.add_expenditure

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moneyapp.api.models.Bill
import com.example.moneyapp.api.models.Category
import com.example.moneyapp.api.models.ExpenditureCategory
import com.example.moneyapp.api.models.NewExpenditure
import com.example.moneyapp.api.services.BillService
import com.example.moneyapp.api.services.CategoryService
import com.example.moneyapp.api.services.TransactionService

class CreateExpenditureViewModel() : ViewModel() {
    private val _createExpenditureResult = MutableLiveData<CreateExpenditureResult>()
    val createExpenditureResult: LiveData<CreateExpenditureResult> = _createExpenditureResult

    val listOfBills: MutableLiveData<List<Bill>> by lazy {
        MutableLiveData<List<Bill>>()
    }

    val listOfCategories: MutableLiveData<List<ExpenditureCategory>> by lazy {
        MutableLiveData<List<ExpenditureCategory>>()
    }

    fun createExpenditure(sum: Int, billId: Int, categoryId: Int) {
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

    fun loadBills() {
        val apiService = BillService()

        apiService.getbill {
            if (it != null) {
                Log.d("CreateIncomeView", "bills loaded")
                listOfBills.value = it.bills
            }
        }
    }

    fun loadCategories() {
        val apiService = CategoryService()

        apiService.getExpenditureCategories() {
            if (it != null) {
                Log.d("CreateIncomeView", "categories loaded")
                listOfCategories.value = it.expenditureCategories
            }
        }
    }
}

