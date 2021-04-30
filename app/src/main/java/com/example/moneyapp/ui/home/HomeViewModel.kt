package com.example.moneyapp.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneyapp.api.models.Bill
import com.example.moneyapp.api.services.BillService
import com.example.moneyapp.database.getDatabase
import com.example.moneyapp.repository.BillRepository
import kotlinx.coroutines.launch
import java.io.IOException

class HomeViewModel : ViewModel() {
    private val billRepository = BillRepository(getDatabase())
    val bills = billRepository.listOfBills

    init {
        refreshDataFromRepository()
    }

    fun loadBills() {

        Log.d("homeviewModel", billRepository.listOfBills.value.toString())
    }

    private fun refreshDataFromRepository() {
        billRepository.refreshBills()
        }
    }
