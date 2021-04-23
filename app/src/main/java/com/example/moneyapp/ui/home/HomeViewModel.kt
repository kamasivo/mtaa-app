package com.example.moneyapp.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moneyapp.api.services.BillUserService

class HomeViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text


    fun loadBills() {
        val apiService = BillUserService()

        apiService.getbill {
        if (it != null) {
            Log.d("HomeViewModel", it)
        }
    }
    }
}