package com.example.moneyapp.ui.detail_bill

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moneyapp.R
import com.example.moneyapp.api.models.Bill
import com.example.moneyapp.api.models.NewBill
import com.example.moneyapp.api.models.UpdateBill
import com.example.moneyapp.api.services.BillService
import com.example.moneyapp.ui.newbill.NewBillFormState
import com.example.moneyapp.ui.newbill.NewBillResult


class DetailBillViewModel() : ViewModel() {
    val bill: MutableLiveData<Bill> by lazy {
        MutableLiveData<Bill>()
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

    private val _newBillForm = MutableLiveData<NewBillFormState>()
    val newBillFormState: LiveData<NewBillFormState> = _newBillForm

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
                Log.d("NewBillViewModel", it)
                if (it == "OK") {
                    _newBillResult.value = NewBillResult(success = true)
                } else {
                    _newBillResult.value = NewBillResult(error = it)
                }
            }
        }
    }

}