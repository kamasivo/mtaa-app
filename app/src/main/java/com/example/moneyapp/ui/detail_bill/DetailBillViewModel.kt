package com.example.moneyapp.ui.detail_bill

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moneyapp.api.models.Bill
import com.example.moneyapp.api.models.Transaction
import com.example.moneyapp.api.models.UpdateBill
import com.example.moneyapp.api.services.BillService
import com.example.moneyapp.api.services.TransactionService
import com.example.moneyapp.ui.new_bill.NewBillResult


class DetailBillViewModel() : ViewModel() {

    val bill: MutableLiveData<Bill> by lazy {
        MutableLiveData<Bill>()
    }
    val listOfExpenditures: MutableLiveData<List<Transaction>> by lazy {
        MutableLiveData<List<Transaction>>()
    }
    val listOfIncomes: MutableLiveData<List<Transaction>> by lazy {
        MutableLiveData<List<Transaction>>()
    }

    fun loadIncomes(billId: Int) {
        val apiService = TransactionService()

        apiService.getIncome(billId) {
            if (it != null) {
                Log.d("BillViewModel", "incomes loaded")
                listOfIncomes.value = it.transactions
            }
        }
    }

    fun loadExpenditures(billId: Int) {
        val apiService = TransactionService()

        apiService.getExpenditure(billId) {
            if (it != null) {
                Log.d("BillViewModel", "expenditures loaded")
                listOfExpenditures.value = it.transactions
            }
        }
    }

    fun load(billId: Int) {
        val apiService = BillService()

        apiService.getBillById(billId) {
            if (it != null) {
                Log.d("DetailBillViewModel", it.toString())
                bill.value = it
                Log.d("DetailBillViewModel", bill.value.toString())
            }
        }
    }

    private val _newBillResult = MutableLiveData<NewBillResult>()
    val newBillResult: LiveData<NewBillResult> = _newBillResult

    fun updateBill(name: String, incomePercents: Int, description: String, sum: Int, billId: Int) {
        Log.d("UpdateBillViewModel", "newBill initiated");
        val apiService = BillService()

        val billInfo = UpdateBill(
                name = name,
                incomePercents = incomePercents,
                description = description,
                sum = sum,
                billId = billId
        )
        apiService.updateBill(billInfo) {
            if (it != null) {
//                Log.d("NewBillViewModel", it)
                if (it == "OK") {
                    _newBillResult.value = NewBillResult(success = true)
                } else {
                    _newBillResult.value = NewBillResult(error = it)
                }
            }
        }
    }

}