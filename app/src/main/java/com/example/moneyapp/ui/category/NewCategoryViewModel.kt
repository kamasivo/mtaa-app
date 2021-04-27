package com.example.moneyapp.ui.category

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moneyapp.R
import com.example.moneyapp.api.models.NewCategory
import com.example.moneyapp.api.services.CategoryService

class NewCategoryViewModel() : ViewModel() {
    private val _newCategoryForm = MutableLiveData<NewCategoryFormState>()
    val newCategoryFormState: LiveData<NewCategoryFormState> = _newCategoryForm

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

    fun newCategoryDataChanged(name: String, incomePercents: Int, description: String, sum: Int) {
        if (!isNameValid(name)) {
            _newCategoryForm.value = NewCategoryFormState(nameError = R.string.invalid_name)
        } else {
            _newCategoryForm.value = NewCategoryFormState(isDataValid = true)
        }
    }


    private fun isNameValid(username: String): Boolean {
        return username.length > 1
    }
}
