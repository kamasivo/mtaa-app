package com.example.moneyapp.ui.newbill

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moneyapp.api.models.NewBillModel
import com.example.moneyapp.api.services.PostBillService

class NewBillViewModel() : ViewModel() {
    private val _newBillForm = MutableLiveData<NewBillFormState>()
    val newBillFormState: LiveData<NewBillFormState> = _newBillForm

    private val _newBillResult = MutableLiveData<NewBillResult>()
    val newBillResult: LiveData<NewBillResult> = _newBillResult

    fun register(name: String, incomePercents: Int, description: String, sum: Int) {
        Log.d("NewBillViewModel", "newbill initiated");
        val apiService = PostBillService()

        val billInfo = NewBillModel(
                name = name,
                incomePercents = 123,
                description = description,
                sum = 123
        )
//        apiService.addBill(billInfo) {
//            if (it != null) {
//                Log.d("NewBillViewModel", it)
//                if (it == "OK") {
//                    _newBillResult.value = NewBillResult(success = true)
//                } else {
//                    _newBillResult.value = NewBillResult(error = R.string.login_failed)
//                }
//            }
//        }
    }

//    fun newBillDataChanged(username: String, password: String, email: String) {
//        if (!isEmailValid(email)) {
//            _newBillForm.value = newBillFormState(emailError = R.string.invalid_email)
//        } else if (!isPasswordValid(password)) {
//            _newBillForm.value = newBillFormState(passwordError = R.string.invalid_password)
//        } else if (!isNameValid(username)){
//            _newBillForm.value = newBillFormState(usernameError = R.string.invalid_username)
//        } else {
//            _newBillForm.value = newBillFormState(isDataValid = true)
//        }
//    }
//
//    // A placeholder username validation check
//    private fun isEmailValid(email: String): Boolean {
//        if(email.contains('@') && email.length > 4) {
//            return true
//        }
//        return false
//    }
//
//    // A placeholder password validation check
//    private fun isPasswordValid(password: String): Boolean {
//        return password.length > 4
//    }
//    private fun isNameValid(username: String): Boolean {
//        return username.length > 2
//    }
}