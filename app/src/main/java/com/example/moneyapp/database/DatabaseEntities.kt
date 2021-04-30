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