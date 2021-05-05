package com.example.moneyapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.moneyapp.GlobalApplication

@Dao
interface ExpenditureCategoryDao {
    @Query("SELECT * FROM ExpenditureCategoryEntity")
    fun getCategories(): LiveData<List<ExpenditureCategoryEntity>>

    @Insert
    fun insertAll(expenditureCategory: List<ExpenditureCategoryEntity>)

    @Insert
    fun insertOne(expenditureCategory: ExpenditureCategoryEntity)

    @Query("DELETE FROM ExpenditureCategoryEntity")
    fun deleteAll()

    @Query("DELETE FROM ExpenditureCategoryEntity WHERE id IN (:categoryId)")
    fun delete(categoryId: Int)
}

@Database(entities = [ExpenditureCategoryEntity::class], version = 1)
abstract class ExpenditureCategoriesDatabase: RoomDatabase() {
    abstract val expenditureCategoryDao: ExpenditureCategoryDao
}

private lateinit var INSTANCE: ExpenditureCategoriesDatabase

fun getExpenditureCategoryDatabase(): ExpenditureCategoriesDatabase {
    val context = GlobalApplication.appContext!!;
    synchronized(ExpenditureCategoriesDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                ExpenditureCategoriesDatabase::class.java,
                "expenditureCategory"
            ).allowMainThreadQueries().build()
            //todo allow main thread is not ideal
        }
    }
    return INSTANCE
}