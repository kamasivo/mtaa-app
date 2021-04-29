package com.example.moneyapp.ui.update_transaction

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moneyapp.api.models.UpdateTransaction
import com.example.moneyapp.api.models.UserProfile
import com.example.moneyapp.api.services.BillService
import com.example.moneyapp.api.services.TransactionService

class UpdateTransactionViewModel() : ViewModel() {

    val user: MutableLiveData<UserProfile> by lazy {
        MutableLiveData<UserProfile>()
    }

    private val _updateTransactionResult = MutableLiveData<UpdateTransactionResult>()
    val newBillResult: LiveData<UpdateTransactionResult> = _updateTransactionResult

    fun updateTransaction(sum: Double, transactionId: Int) {
        Log.d("NewBillViewModel", "newBill initiated");
        val apiService = TransactionService()

        val transactionInfo = UpdateTransaction(
            transactionId = transactionId,
            sum = sum
        )
        apiService.updateTransaction(transactionInfo) {
            if (it != null) {
                Log.d("updateTransactionViewModel", it)
                if (it == "OK") {
                    _updateTransactionResult.value = UpdateTransactionResult(success = true)
                } else {
                    _updateTransactionResult.value = UpdateTransactionResult(error = it)
                }
            }
        }
    }
    fun deleteTransaction(categoryId: Int) {
        val apiService = TransactionService()
        apiService.deleteTransaction(categoryId) {
            if (it != null) {
                Log.d("updateTransactionViewModel", it)
                if (it == "OK") {
                    _updateTransactionResult.value = UpdateTransactionResult(success = true)
                } else {
                    _updateTransactionResult.value = UpdateTransactionResult(error = it)
                }
            }
        }

    }

}
