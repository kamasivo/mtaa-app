package com.example.moneyapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.moneyapp.api.models.Category
import com.example.moneyapp.api.models.NewCategory
import com.example.moneyapp.api.models.NewType
import com.example.moneyapp.api.services.CategoryService
import com.example.moneyapp.database.*
import kotlin.random.Random


class IncomeCategoryRepository(private val database: IncomeCategoriesDatabase) {
    val listOfIncomeCategories: LiveData<List<Category>> = Transformations.map(database.incomeCategoryDao.getCategories()) {
        it.asIncomeCategoryModel()
    }

    fun refreshIncomeCategories() {

            val apiService = CategoryService()
            apiService.getIncomeCategories {
                if (it != null) {
                    Log.d("HomeViewModel", "Incomecategories loaded")
                    insertToRepository(it.incomeCategories)
                }
            }

    }

    fun deleteIncomeCategory(categoryId: Int) {
        Log.d("NewTypeViewModel", "newType initiated");
        val apiService = CategoryService()

        apiService.deleteCategory(categoryId) {}
        database.incomeCategoryDao.delete(categoryId)
    }

    // toto je insert do Room
    private fun insertToRepository(incomeCategories: List<Category>) {
            // make database entity from model
            val incomeCategoryEntity = incomeCategories.map {
                IncomeCategoryEntity(
                    id = it.id!!,
                    name = it.name!!
                )
            }
            Log.d("incomeCategoryrepository", incomeCategoryEntity.toString())
            database.incomeCategoryDao.deleteAll()
            database.incomeCategoryDao.insertAll(incomeCategoryEntity!!)
            Log.d("incomeCategoryrepository", "successfully added to room?")
    }

    fun insertIncomeCategoryToRepository(incomeCategory: NewType) {
        val randId = Random.nextInt(100, 60000)

        val incomeCategoryEntity = IncomeCategoryEntity (
            id = randId,
            name = incomeCategory.name!!
        )

        Log.d("incomerepository", incomeCategoryEntity.toString())
//        database.billDao.deleteAll()
        database.incomeCategoryDao.insertOne(incomeCategoryEntity!!)
        Log.d("incomerepository", "successfully added to room?")
    }


}