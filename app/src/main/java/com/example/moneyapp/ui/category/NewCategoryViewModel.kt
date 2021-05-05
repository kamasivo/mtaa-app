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
import com.example.moneyapp.database.getDatabase
import com.example.moneyapp.database.getExpenditureCategoryDatabase
import com.example.moneyapp.repository.BillRepository
import com.example.moneyapp.repository.ExpenditureCategoryRepository

class NewCategoryViewModel() : ViewModel() {

//    private val billRepository = BillRepository(getDatabase())
//    val bills = billRepository.listOfBills
//
//    init {
//        refreshDataFromRepository()
//    }

    private val expenditureCategoryRepository = ExpenditureCategoryRepository(getExpenditureCategoryDatabase())
    val expenditureCategories = expenditureCategoryRepository.listOfExpenditureCategories

    init {
        refreshCategoryDataFromRepository()
    }

    fun loadExpenditureCategories() {
        Log.d("homeviewModel", expenditureCategoryRepository.listOfExpenditureCategories.value.toString())
    }

    private fun refreshCategoryDataFromRepository() {
        expenditureCategoryRepository.refreshCategories()
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
        expenditureCategoryRepository.insertExpenditureCategoryToRepository(categoryInfo)
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
//        Log.d("homeviewModel", billRepository.listOfBills.value.toString())
        val apiService = BillService()

        apiService.getbill {
            if (it != null) {
                Log.d("NewCategoryView", "bills loaded")
                listOfBills.value = it.bills
            }
        }
    }

//    private fun refreshDataFromRepository() {
//        billRepository.refreshBills()
//    }


}
