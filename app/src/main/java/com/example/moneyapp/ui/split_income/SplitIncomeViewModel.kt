package com.example.moneyapp.ui.split_income

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moneyapp.api.models.Category
import com.example.moneyapp.api.models.NewSplitIncome
import com.example.moneyapp.api.services.CategoryService
import com.example.moneyapp.api.services.TransactionService

class SplitIncomeViewModel() : ViewModel() {

    private val _splitIncomeResult = MutableLiveData<SplitIncomeResult>()
    val splitIncomeResult: LiveData<SplitIncomeResult> = _splitIncomeResult

    val listOfCategories: MutableLiveData<List<Category>> by lazy {
        MutableLiveData<List<Category>>()
    }

    fun splitIncome(sum: Int, categoryId: Int) {
        Log.d("SplitIncomeViewModel", "splitIncome initiated");
        val apiService = TransactionService()

        val incomeInfo = NewSplitIncome(
            sum = sum,
            categoryId = categoryId
        )
        apiService.addSplitIncome(incomeInfo) {
            if (it != null) {
                Log.d("SplitIncomeViewModel", it)
                if (it == "OK") {
                    _splitIncomeResult.value = SplitIncomeResult(success = true)
                } else {
                    _splitIncomeResult.value = SplitIncomeResult(error = it)
                }
            }
        }
    }

    fun loadCategories() {
        val apiService = CategoryService()

        apiService.getIncomeCategories() {
            if (it != null) {
                Log.d("CreateIncomeView", "categories loaded")
                Log.d("CreateIncomeView", it.incomeCategories.toString())
                listOfCategories.value = it.incomeCategories
            }
        }
    }
}
