package com.example.moneyapp.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moneyapp.api.models.Bill
import com.example.moneyapp.api.services.BillUserService

class HomeViewModel : ViewModel() {
    val listOfBills: MutableLiveData<List<Bill>> by lazy {
        MutableLiveData<List<Bill>>()
    }

    fun loadBills() {
        val apiService = BillUserService()


        apiService.getbill {
        if (it != null) {
            Log.d("HomeViewModel", "bills loaded")
            listOfBills.value = it.bills
        }
    }
    }
}