package com.example.moneyapp.ui.add_income

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moneyapp.api.models.Bill
import com.example.moneyapp.api.models.Category
import com.example.moneyapp.api.models.NewIncome
import com.example.moneyapp.api.services.BillService
import com.example.moneyapp.api.services.CategoryService
import com.example.moneyapp.api.services.TransactionService

class CreateIncomeViewModel() : ViewModel() {
    private val _createIncomeForm = MutableLiveData<CreateIncomeFormState>()
    val createIncomeFormState: LiveData<CreateIncomeFormState> = _createIncomeForm

    private val _createIncomeResult = MutableLiveData<CreateIncomeResult>()
    val createIncomeResult: LiveData<CreateIncomeResult> = _createIncomeResult

    val listOfBills: MutableLiveData<List<Bill>> by lazy {
        MutableLiveData<List<Bill>>()
    }

    val listOfCategories: MutableLiveData<List<Category>> by lazy {
        MutableLiveData<List<Category>>()
    }

    fun createIncome(sum: Int, billId: Int, categoryId: Int) {
        val apiService = TransactionService()

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

        apiService.getIncomeCategories() {
            if (it != null) {
                Log.d("CreateIncomeView", "categories loaded")
                listOfCategories.value = it.bills
            }
        }
    }
}
