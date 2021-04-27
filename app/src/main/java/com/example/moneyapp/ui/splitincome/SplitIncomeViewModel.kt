package com.example.moneyapp.ui.createincome

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moneyapp.api.models.NewSplitIncome
import com.example.moneyapp.api.services.PostSplitIncomeService

class SplitIncomeViewModel() : ViewModel() {
    private val _splitIncomeForm = MutableLiveData<SplitIncomeFormState>()
    val spliitIncomeFormState: LiveData<SplitIncomeFormState> = _splitIncomeForm

    private val _splitIncomeResult = MutableLiveData<SplitIncomeResult>()
    val splitIncomeResult: LiveData<SplitIncomeResult> = _splitIncomeResult

    fun splitIncome(sum: Int, categoryId: Int) {
        Log.d("SplitIncomeViewModel", "splitIncome initiated");
        val apiService = PostSplitIncomeService()

        val incomeInfo = NewSplitIncome(
            sum = sum,
            categoryId = categoryId
        )
        apiService.addSplitIncome(incomeInfo) {
            if (it != null) {
                Log.d("SplitIncomeViewModel", it)
                if (it == "OK") {
                    _splitIncomeResult.value = SplitIncomeResult(success = true)
                } else {
                    _splitIncomeResult.value = SplitIncomeResult(error = it)
                }
            }
        }
    }

    fun splitIncomeDataChanged(sum: Int) {
        if (!isSumValid(sum)) {
            _splitIncomeForm.value = SplitIncomeFormState(sumError = "Invalid sum")
        } else {
            _splitIncomeForm.value = SplitIncomeFormState(isDataValid = true)
        }
    }



    private fun isSumValid(incomeSum: Int): Boolean {
        return incomeSum > 0
    }
}
