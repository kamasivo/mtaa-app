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
import com.example.moneyapp.database.getDatabase
import com.example.moneyapp.database.getExpenditureCategoryDatabase
import com.example.moneyapp.database.getExpenditureDatabase
import com.example.moneyapp.database.getIncomeDatabase
import com.example.moneyapp.repository.BillRepository
import com.example.moneyapp.repository.ExpenditureCategoryRepository
import com.example.moneyapp.repository.ExpenditureRepository
import com.example.moneyapp.repository.IncomeRepository

class CreateExpenditureViewModel() : ViewModel() {
    private val expenditureRepository = ExpenditureRepository(getExpenditureDatabase())
    private val billRepository = BillRepository(getDatabase())
    val bills = billRepository.listOfBills

    private val expenditureCategoryRepository = ExpenditureCategoryRepository(getExpenditureCategoryDatabase())
    val expenditureCategories = expenditureCategoryRepository.listOfExpenditureCategories

    private val _createExpenditureResult = MutableLiveData<CreateExpenditureResult>()
    val createExpenditureResult: LiveData<CreateExpenditureResult> = _createExpenditureResult

//    private val expenditureRepository = ExpenditureRepository(getExpenditureDatabase())
//    val expenditures = expenditureRepository.listOfExpenditures
//
//    init {
//        refreshDataFromRepository()
//    }
//    fun loadExpenditures() {
//        Log.d("homeviewModel", expenditureRepository.listOfExpenditures.value.toString())
//    }
//
//    private fun refreshDataFromRepository(billId: Int) {
//        expenditureRepository.refreshTransactions(billId)
//    }

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

