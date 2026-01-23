package com.busra.expensetrackerapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.busra.expensetrackerapp.data.dao.ExpenseDao
import com.busra.expensetrackerapp.data.entity.Expense

@Database(entities = [Expense::class], version = 2)
abstract class ExpenseDatabase : RoomDatabase() {

    abstract fun expenseDao(): ExpenseDao

    companion object {
        @Volatile
        private var INSTANCE: ExpenseDatabase? = null

        fun getDatabase(context: Context): ExpenseDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ExpenseDatabase::class.java,
                    "expense_db"
                )
                    .fallbackToDestructiveMigration() // 🔴 BU ŞART
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}
