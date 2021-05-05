package com.example.moneyapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BillEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val incomePercents: Double?,
    val description: String?,
    val sum: Double?)

@Entity
data class ExpenditureCategoryEntity(
        @PrimaryKey val id: Int,
        val name: String,
        val expenditureTypes: Int)

@Entity
data class IncomeCategoryEntity(
        @PrimaryKey val id: Int,
        val name: String)


/**
 * Map DatabaseVideos to domain entities
 */
fun List<BillEntity>.asDomainModel(): List<com.example.moneyapp.api.models.Bill> {
    return map {
        com.example.moneyapp.api.models.Bill(
            id = it.id,
            name = it.name,
            description = it.description,
            incomePercents = it.incomePercents,
            sum = it.sum)
    }
}

fun List<ExpenditureCategoryEntity>.asExpenditureCategoryModel(): List<com.example.moneyapp.api.models.ExpenditureCategory> {
    return map {
        com.example.moneyapp.api.models.ExpenditureCategory(
            id = it.id,
            name = it.name,
            expenditureTypes = it.expenditureTypes)
    }
}

fun List<IncomeCategoryEntity>.asIncomeCategoryModel(): List<com.example.moneyapp.api.models.Category> {
    return map {
        com.example.moneyapp.api.models.Category(
                id = it.id,
                name = it.name)
    }
}