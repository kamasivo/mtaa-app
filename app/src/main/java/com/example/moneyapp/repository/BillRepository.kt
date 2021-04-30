package com.example.moneyapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.moneyapp.api.models.Bill
import com.example.moneyapp.api.services.BillService
import com.example.moneyapp.database.BillsDatabase
import com.example.moneyapp.database.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class BillRepository(private val database: BillsDatabase) {
    val listOfBills: LiveData<List<Bill>> = Transformations.map(database.billDao.getBills()) {
        it.asDomainModel()
    }

    fun refreshBills() {

            val apiService = BillService()
            apiService.getbill {
                if (it != null) {
                    Log.d("HomeViewModel", "bills loaded")
                    insertToRepository(it.bills)
                }
            }

    }

    fun deleteBill(billId: Int) {
        Log.d("NewTypeViewModel", "newType initiated");
        val apiService = BillService()

        apiService.deleteBill(billId) {}
        database.billDao.delete(billId)
    }

    private fun insertToRepository(bills: List<Bill>) {
            // make database entity from model
            val billEntity = bills.map {
                com.example.moneyapp.database.Bill(
                    id = it.id!!,
                    name = it.name!!,
                    incomePercents = it.incomePercents!!,
                    description = it.description!!,
                    sum = it.sum!!
                )
            }
            Log.d("billrepository", billEntity.toString())
            database.billDao.deleteAll()
            database.billDao.insertAll(billEntity!!)
            Log.d("billrepository", "successfully added to room?")
    }
}