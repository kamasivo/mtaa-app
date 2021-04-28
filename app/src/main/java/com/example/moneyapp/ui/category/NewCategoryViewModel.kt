package com.example.moneyapp.ui.category

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moneyapp.api.models.Bill
import com.example.moneyapp.api.models.Category
import com.example.moneyapp.api.models.ExpenditureCategory
import com.example.moneyapp.api.models.NewCategory
import com.example.moneyapp.api.services.BillService
import com.example.moneyapp.api.services.CategoryService

class NewCategoryViewModel() : ViewModel() {

    val listOfExpenditureCategory: MutableLiveData<List<ExpenditureCategory>> by lazy {
        MutableLiveData<List<ExpenditureCategory>>()
    }

    fun loadExpenditureCategories() {
        val apiService = CategoryService()

        apiService.getExpenditureCategories {
            if (it != null) {
                Log.d("HomeViewModel", "bills loaded")
                listOfExpenditureCategory.value = it.expenditureCategories
            }
        }
    }

    private val _newCategoryForm = MutableLiveData<NewCategoryFormState>()
    val newCategoryFormState: LiveData<NewCategoryFormState> = _newCategoryForm

    val listOfBills: MutableLiveData<List<Bill>> by lazy {
        MutableLiveData<List<Bill>>()
    }

    private val _newCategoryResult = MutableLiveData<NewCategoryResult>()
    val newCategoryResult: LiveData<NewCategoryResult> = _newCategoryResult

    fun newCategory(name: String, billId: Int) {
        Log.d("NewCategoryViewModel", "newCategory initiated");
        val apiService = CategoryService()

        val categoryInfo = NewCategory(
            name = name,
            billId = billId
        )
        apiService.addCategory(categoryInfo) {
            if (it != null) {
                Log.d("NewCategoryViewModel", it)
                if (it == "OK") {
                    _newCategoryResult.value = NewCategoryResult(success = true)
                } else {
                    _newCategoryResult.value = NewCategoryResult(error = it)
                }
            }
        }
    }

    fun loadBills() {
        val apiService = BillService()

        apiService.getbill {
            if (it != null) {
                Log.d("NewCategoryView", "bills loaded")
                listOfBills.value = it.bills
            }
        }
    }
}
