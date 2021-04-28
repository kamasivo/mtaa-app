package com.example.moneyapp.ui.create_income_category

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moneyapp.R
import com.example.moneyapp.api.models.Bill
import com.example.moneyapp.api.models.Category
import com.example.moneyapp.api.models.NewType
import com.example.moneyapp.api.services.BillService
import com.example.moneyapp.api.services.CategoryService
import com.example.moneyapp.api.services.PostTypeService

class NewTypeViewModel() : ViewModel() {

    val listOfIncomeCategory: MutableLiveData<List<Category>> by lazy {
        MutableLiveData<List<Category>>()
    }

    fun loadIncomeCategories() {
        val apiService = CategoryService()

        apiService.getIncomeCategories {
            if (it != null) {
                Log.d("HomeViewModel", "bills loaded")
                listOfIncomeCategory.value = it.incomeCategories
            }
        }
    }

    private val _newTypeForm = MutableLiveData<NewTypeFormState>()
    val newTypeFormState: LiveData<NewTypeFormState> = _newTypeForm

    private val _newTypeResult = MutableLiveData<NewTypeResult>()
    val newTypeResult: LiveData<NewTypeResult> = _newTypeResult

    fun newType(name: String) {
        Log.d("NewTypeViewModel", "newType initiated");
        val apiService = PostTypeService()

        val typeInfo = NewType(
            name = name
        )
        apiService.addType(typeInfo) {
            if (it != null) {
                Log.d("NewTypeViewModel", it)
                if (it == "OK") {
                    _newTypeResult.value = NewTypeResult(success = true)
                } else {
                    _newTypeResult.value = NewTypeResult(error = it)
                }
            }
        }
    }

    fun newTypeDataChanged(name: String) {
        if (!isNameValid(name)) {
            _newTypeForm.value = NewTypeFormState(nameError = R.string.invalid_name)
        } else {
            _newTypeForm.value = NewTypeFormState(isDataValid = true)
        }
    }


    private fun isNameValid(username: String): Boolean {
        return username.length > 1
    }
}
