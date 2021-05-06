package com.example.moneyapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.moneyapp.api.models.ExpenditureCategory
import com.example.moneyapp.api.models.NewCategory
import com.example.moneyapp.api.models.NewExpenditure
import com.example.moneyapp.api.models.Transaction
import com.example.moneyapp.api.services.CategoryService
import com.example.moneyapp.api.services.TransactionService
import com.example.moneyapp.database.*
import kotlin.random.Random


class ExpenditureRepository(private val database: ExpendituresDatabase) {
    val listOfExpenditures: LiveData<List<Transaction>> = Transformations.map(database.expenditureDao.getTransactions()) {
        it.asExpenditureModel()
    }

    fun refreshTransactions(billId: Int) {

            val apiService = TransactionService()
            apiService.getExpenditure(billId) {
                if (it != null) {
                    Log.d("HomeViewModel", "expenditures loaded")
                    insertToRepository(it.transactions)
                }
            }

    }

    fun deleteExpenditure(transactionId: Int) {
        Log.d("NewTypeViewModel", "newType initiated");
        val apiService = TransactionService()

        apiService.deleteTransaction(transactionId) {}
        database.expenditureDao.delete(transactionId)
    }

    // toto je insert do Room
    private fun insertToRepository(expenditures: List<Transaction>) {
            // make database entity from model
            val expenditureEntity = expenditures.map {
                ExpenditureEntity(
                    id = it.id!!,
                    sum = it.sum!!,
                    billId = it.billId!!
                )
            }
            Log.d("expenditurerepository", expenditureEntity.toString())
            database.expenditureDao.deleteAll()
            database.expenditureDao.insertAll(expenditureEntity!!)
            Log.d("expenditurerepository", "successfully added to room?")
    }

    fun insertExpenditureToRepository(expenditure: NewExpenditure) {
        val randId = Random.nextInt(100, 60000)

        val expenditureEntity = ExpenditureEntity (
            id = randId,
            sum = expenditure.sum.toString().toDouble(),
            billId = expenditure.billId!!,
        )

        Log.d("expenditurerepository", expenditureEntity.toString())
//        database.billDao.deleteAll()
        database.expenditureDao.insertOne(expenditureEntity!!)
        Log.d("expenditurerepository", "successfully added to room?")
    }


}