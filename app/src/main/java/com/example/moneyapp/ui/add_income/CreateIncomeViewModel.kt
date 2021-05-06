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
import com.example.moneyapp.database.getDatabase
import com.example.moneyapp.database.getExpenditureCategoryDatabase
import com.example.moneyapp.database.getIncomeCategoryDatabase
import com.example.moneyapp.database.getIncomeDatabase
import com.example.moneyapp.repository.BillRepository
import com.example.moneyapp.repository.ExpenditureCategoryRepository
import com.example.moneyapp.repository.IncomeCategoryRepository
import com.example.moneyapp.repository.IncomeRepository

class CreateIncomeViewModel() : ViewModel() {
    private val incomeRepository = IncomeRepository(getIncomeDatabase())
    private val billRepository = BillRepository(getDatabase())
    val bills = billRepository.listOfBills

    private val incomeCategoryRepository = IncomeCategoryRepository(getIncomeCategoryDatabase())
    val incomeCategories = incomeCategoryRepository.listOfIncomeCategories

    private val _createIncomeResult = MutableLiveData<CreateIncomeResult>()
    val createIncomeResult: LiveData<CreateIncomeResult> = _createIncomeResult

//    private val incomeRepository = IncomeRepository(getIncomeDatabase())
//    val incomes = incomeRepository.listOfIncomes
//
//    init {
//        refreshDataFromRepository()
//    }
//    fun loadIncomes() {
//        Log.d("homeviewModel", incomeRepository.listOfIncomes.value.toString())
//    }
//
//    private fun refreshDataFromRepository(billId: Int) {
//        incomeRepository.refreshIncomeCategories(billId)
//    }

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
        Log.d("CreateIncomeViewModel", incomeInfo.toString())
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
        incomeRepository.insertIncomeToRepository(incomeInfo)
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
                listOfCategories.value = it.incomeCategories
            }
        }
    }
}
