package com.example.moneyapp.ui.detail_bill

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moneyapp.api.models.Bill
import com.example.moneyapp.api.services.BillService


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
}