package com.example.moneyapp.api.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CategoryArray (
    @SerializedName("incomeCategories")
    @Expose
    val bills: List<Category>
)