package com.example.moneyapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.moneyapp.api.models.*
import com.example.moneyapp.api.services.CategoryService
import com.example.moneyapp.api.services.TransactionService
import com.example.moneyapp.database.*
import kotlin.random.Random


class IncomeRepository(private val database: IncomesDatabase) {
    val listOfIncomes: LiveData<List<Transaction>> = Transformations.map(database.incomeDao.getTransactions()) {
        it.asIncomeModel()
    }

    fun refreshTransaction(billId: Int) {

            val apiService = TransactionService()
            apiService.getIncome(billId) {
                if (it != null) {
                    Log.d("HomeViewModel", "Incomecategories loaded")
                    insertToRepository(it.transactions)
                }
            }

    }

    fun deleteIncome(transactionId: Int) {
        Log.d("NewTypeViewModel", "newType initiated");
        val apiService = TransactionService()

        apiService.deleteTransaction(transactionId) {}
        database.incomeDao.delete(transactionId)
    }

    // toto je insert do Room
    private fun insertToRepository(incomes: List<Transaction>) {
            // make database entity from model
            val incomeEntity = incomes.map {
                IncomeEntity(
                    id = it.id!!,
                    sum = it.sum!!,
                    billId = it.billId!!,
                )
            }
            Log.d("incomerepository", incomeEntity.toString())
            database.incomeDao.deleteAll()
            database.incomeDao.insertAll(incomeEntity!!)
            Log.d("incomerepository", "successfully added to room?")
    }

    fun insertIncomeToRepository(income: NewIncome) {
        val randId = Random.nextInt(100, 60000)

        val incomeEntity = IncomeEntity (
            id = randId,
            sum = income.sum.toString().toDouble(),
            billId = income.billId!!,
        )

        Log.d("incomerepository", incomeEntity.toString())
//        database.billDao.deleteAll()
        database.incomeDao.insertOne(incomeEntity!!)
        Log.d("incomerepository", "successfully added to room?")
    }


}