package com.example.moneyapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.moneyapp.GlobalApplication

@Dao
interface IncomeCategoryDao {
    @Query("SELECT * FROM IncomeCategoryEntity")
    fun getCategories(): LiveData<List<IncomeCategoryEntity>>

    @Insert
    fun insertAll(incomeCategory: List<IncomeCategoryEntity>)

    @Insert
    fun insertOne(incomeCategory: IncomeCategoryEntity)

    @Query("DELETE FROM IncomeCategoryEntity")
    fun deleteAll()

    @Query("DELETE FROM IncomeCategoryEntity WHERE id IN (:categoryId)")
    fun delete(categoryId: Int)
}

@Database(entities = [IncomeCategoryEntity::class], version = 1)
abstract class IncomeCategoriesDatabase: RoomDatabase() {
    abstract val incomeCategoryDao: IncomeCategoryDao
}

private lateinit var INSTANCE: IncomeCategoriesDatabase

fun getIncomeCategoryDatabase(): IncomeCategoriesDatabase {
    val context = GlobalApplication.appContext!!;
    synchronized(IncomeCategoriesDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                    IncomeCategoriesDatabase::class.java,
                "IncomeCategory"
            ).allowMainThreadQueries().build()
            //todo allow main thread is not ideal
        }
    }
    return INSTANCE
}