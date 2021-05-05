package com.example.moneyapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.moneyapp.GlobalApplication

@Dao
interface ExpenditureDao {
    @Query("SELECT * FROM ExpenditureEntity")
    fun getTransactions(): LiveData<List<ExpenditureEntity>>

    @Insert
    fun insertAll(expenditure: List<ExpenditureEntity>)

    @Insert
    fun insertOne(expenditure: ExpenditureEntity)

    @Query("DELETE FROM ExpenditureEntity")
    fun deleteAll()

    @Query("DELETE FROM ExpenditureEntity WHERE id IN (:categoryId)")
    fun delete(categoryId: Int)
}

@Database(entities = [ExpenditureEntity::class], version = 1)
abstract class ExpendituresDatabase: RoomDatabase() {
    abstract val expenditureDao: ExpenditureDao
}

private lateinit var INSTANCE: ExpendituresDatabase

fun getExpenditureDatabase(): ExpendituresDatabase {
    val context = GlobalApplication.appContext!!;
    synchronized(ExpendituresDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                ExpendituresDatabase::class.java,
                "expenditure"
            ).allowMainThreadQueries().build()
            //todo allow main thread is not ideal
        }
    }
    return INSTANCE
}