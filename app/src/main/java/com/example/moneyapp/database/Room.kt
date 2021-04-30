package com.example.moneyapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.moneyapp.GlobalApplication

@Dao
interface BillDao {
    @Query("SELECT * FROM bill")
    fun getBills(): LiveData<List<Bill>>

    @Insert
    fun insertAll(bill: List<Bill>)

    @Query("DELETE FROM bill")
    fun deleteAll()

    @Query("DELETE FROM bill WHERE id IN (:billId)")
    fun delete(billId: Int)
}

@Database(entities = [Bill::class], version = 1)
abstract class BillsDatabase: RoomDatabase() {
    abstract val billDao: BillDao
}

private lateinit var INSTANCE: BillsDatabase

fun getDatabase(): BillsDatabase {
    val context = GlobalApplication.appContext!!;
    synchronized(BillsDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                BillsDatabase::class.java,
                "bill"
            ).allowMainThreadQueries().build()
            //todo allow main thread is not ideal
        }
    }
    return INSTANCE
}