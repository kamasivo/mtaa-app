package com.example.moneyapp.api.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BillsArray (
        @SerializedName("bills")
        @Expose
        private val bills: List<Bill>
)