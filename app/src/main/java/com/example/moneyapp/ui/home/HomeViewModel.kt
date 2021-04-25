package com.example.moneyapp.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moneyapp.api.models.Bill
import com.example.moneyapp.api.services.BillUserService

class HomeViewModel : ViewModel() {
//    lateinit var listOfBills: List<Bill>

    val listOfBills: MutableLiveData<List<Bill>> by lazy {
        MutableLiveData<List<Bill>>()
    }

//    private val _text = MutableLiveData<String>().apply {
//        value = "This is home Fragment"
//    }
//    val text: LiveData<String> = _text

    fun loadBills() {
        val apiService = BillUserService()


        apiService.getbill {
        if (it != null) {
            listOfBills.value = it.bills
        }
    }
    }
}