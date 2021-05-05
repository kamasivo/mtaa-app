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
import com.example.moneyapp.database.getDatabase
import com.example.moneyapp.database.getExpenditureCategoryDatabase
import com.example.moneyapp.database.getIncomeCategoryDatabase
import com.example.moneyapp.repository.ExpenditureCategoryRepository
import com.example.moneyapp.repository.IncomeCategoryRepository

class NewTypeViewModel() : ViewModel() {

    private val incomeCategoryRepository = IncomeCategoryRepository(getIncomeCategoryDatabase())
    val incomeCategories = incomeCategoryRepository.listOfIncomeCategories

    init {
        refreshDataFromRepository()
    }

    fun loadIncomeCategories() {
        Log.d("homeviewModel", incomeCategoryRepository.listOfIncomeCategories.value.toString())
    }

    private fun refreshDataFromRepository() {
        incomeCategoryRepository.refreshIncomeCategories()
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
        incomeCategoryRepository.insertIncomeCategoryToRepository(typeInfo)
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
