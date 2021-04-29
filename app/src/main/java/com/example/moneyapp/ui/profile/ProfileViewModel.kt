package com.example.moneyapp.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moneyapp.R
import com.example.moneyapp.api.models.EditUser
import com.example.moneyapp.api.models.Password
import com.example.moneyapp.api.models.UserLogin
import com.example.moneyapp.api.models.UserProfile
import com.example.moneyapp.api.services.UserService
import com.example.moneyapp.ui.new_bill.NewBillResult


class ProfileViewModel() : ViewModel() {
    val user: MutableLiveData<UserProfile> by lazy {
        MutableLiveData<UserProfile>()
    }

    private val _profileForm = MutableLiveData<ProfilePasswordFormState>()
    val profileFormState: LiveData<ProfilePasswordFormState> = _profileForm

    private val _profilForm = MutableLiveData<ProfileFormState>()
    val profilFormState: LiveData<ProfileFormState> = _profilForm

    private val _profileResult = MutableLiveData<ProfileResult>()
    val profileResult: LiveData<ProfileResult> = _profileResult

    fun updateProfile(name: String, email: String) {
        Log.d("UpdateProfileViewModel", "updateProfile initiated");
        val apiService = UserService()

        val profileInfo = EditUser(
                fullName = name,
                emailAddress = email
        )
        apiService.updateProfile(profileInfo) {
            if (it != null) {
                Log.d("ProfileViewModel", it)
                if (it == "OK") {
                    _profileResult.value = ProfileResult(success = true)
                } else {
                    _profileResult.value = ProfileResult(error = "You entered wrong password")
                }
            }
        }
    }

    fun updatePassword(password: String, newPassword: String) {
        Log.d("UpdateBillViewModel", "newBill initiated");
        val apiService = UserService()

        val passwordInfo = Password(
                password = password,
                newPassword = newPassword
        )
        apiService.updatePassword(passwordInfo) {
            if (it != null) {
                Log.d("ProfileViewModel", it)
                if (it == "OK") {
                    _profileResult.value = ProfileResult(success = true)
                } else {
                    _profileResult.value = ProfileResult(error = it)
                }
            }
        }
    }

    fun passwordDataChanged(newPassword: String, repeatPassword: String) {
        if (!isPasswordValid(newPassword)) {
            _profileForm.value = ProfilePasswordFormState(newPasswordError = R.string.invalid_profilepassword)
        } else if (!isRepeatPasswordValid(newPassword, repeatPassword)){
            _profileForm.value = ProfilePasswordFormState(repeatPasswordError = R.string.invalid_repeatPassword)
        } else {
            _profileForm.value = ProfilePasswordFormState(isDataValid = true)
        }
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 4
    }
    private fun isRepeatPasswordValid(password: String, repeatPassword: String): Boolean {
        return password == repeatPassword
    }

    fun emailDataChanged(email: String) {
        if (!isEmailValid(email)) {
            _profilForm.value = ProfileFormState(emailError = R.string.invalid_email)
        } else {
            _profilForm.value = ProfileFormState(isDataValid = true)
        }
    }

    private fun isEmailValid(email: String): Boolean {
        if(email.contains('@') && email.length > 4) {
            return true
        }
        return false
    }
}