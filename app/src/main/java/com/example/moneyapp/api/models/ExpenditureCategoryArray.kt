package com.example.moneyapp.api.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ExpenditureCategoryArray (
    @SerializedName("expenditureCategories")
    @Expose
    val expenditureCategories: List<ExpenditureCategory>
)