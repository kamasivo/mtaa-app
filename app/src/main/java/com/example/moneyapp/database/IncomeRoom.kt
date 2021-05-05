package com.example.moneyapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.moneyapp.GlobalApplication

@Dao
interface IncomeDao {
    @Query("SELECT * FROM IncomeEntity")
    fun getTransactions(): LiveData<List<IncomeEntity>>

    @Insert
    fun insertAll(income: List<IncomeEntity>)

    @Insert
    fun insertOne(income: IncomeEntity)

    @Query("DELETE FROM IncomeEntity")
    fun deleteAll()

    @Query("DELETE FROM IncomeEntity WHERE id IN (:categoryId)")
    fun delete(categoryId: Int)
}

@Database(entities = [IncomeEntity::class], version = 1)
abstract class IncomesDatabase: RoomDatabase() {
    abstract val incomeDao: IncomeDao
}

private lateinit var INSTANCE: IncomesDatabase

fun getIncomeDatabase(): IncomesDatabase {
    val context = GlobalApplication.appContext!!;
    synchronized(IncomesDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                    IncomesDatabase::class.java,
                "Income"
            ).allowMainThreadQueries().build()
            //todo allow main thread is not ideal
        }
    }
    return INSTANCE
}