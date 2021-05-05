package com.example.moneyapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.moneyapp.api.models.ExpenditureCategory
import com.example.moneyapp.api.models.NewCategory
import com.example.moneyapp.api.services.CategoryService
import com.example.moneyapp.database.*
import kotlin.random.Random


class ExpenditureCategoryRepository(private val database: ExpenditureCategoriesDatabase) {
    val listOfExpenditureCategories: LiveData<List<ExpenditureCategory>> = Transformations.map(database.expenditureCategoryDao.getCategories()) {
        it.asExpenditureCategoryModel()
    }

    fun refreshCategories() {

            val apiService = CategoryService()
            apiService.getExpenditureCategories {
                if (it != null) {
                    Log.d("HomeViewModel", "expenditurecategories loaded")
                    insertToRepository(it.expenditureCategories)
                }
            }

    }

    fun deleteExpenditureCategory(categoryId: Int) {
        Log.d("NewTypeViewModel", "newType initiated");
        val apiService = CategoryService()

        apiService.deleteCategory(categoryId) {}
        database.expenditureCategoryDao.delete(categoryId)
    }

    // toto je insert do Room
    private fun insertToRepository(expenditureCategories: List<ExpenditureCategory>) {
            // make database entity from model
            val expenditureCategoryEntity = expenditureCategories.map {
                ExpenditureCategoryEntity(
                    id = it.id!!,
                    name = it.name!!,
                    expenditureTypes = it.expenditureTypes!!
                )
            }
            Log.d("expenditureCategoryrepository", expenditureCategoryEntity.toString())
            database.expenditureCategoryDao.deleteAll()
            database.expenditureCategoryDao.insertAll(expenditureCategoryEntity!!)
            Log.d("expenditureCategoryrepository", "successfully added to room?")
    }

    fun insertExpenditureCategoryToRepository(expenditureCategory: NewCategory) {
        val randId = Random.nextInt(100, 60000)

        val expenditureCategoryEntity = ExpenditureCategoryEntity (
            id = randId,
            name = expenditureCategory.name!!,
            expenditureTypes = expenditureCategory.billId!!
        )

        Log.d("expenditurerepository", expenditureCategoryEntity.toString())
//        database.billDao.deleteAll()
        database.expenditureCategoryDao.insertOne(expenditureCategoryEntity!!)
        Log.d("expenditurerepository", "successfully added to room?")
    }


}