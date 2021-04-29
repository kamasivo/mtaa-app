package com.example.moneyapp.api.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ExpenditureTransactionArray (
        @SerializedName("expenditureTransaction")
        @Expose
        val transactions: List<Transaction>
)