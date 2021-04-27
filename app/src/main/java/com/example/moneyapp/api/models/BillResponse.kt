package com.example.moneyapp.api.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BillResponse (
    @SerializedName("bill")
    @Expose
    public val bill: Bill
)