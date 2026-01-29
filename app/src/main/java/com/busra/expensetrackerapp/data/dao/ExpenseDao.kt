package com.busra.expensetrackerapp.data.dao


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.busra.expensetrackerapp.data.entity.CategoryTotal
import com.busra.expensetrackerapp.data.entity.Expense

@Dao
interface ExpenseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: Expense)

    @Query("SELECT * FROM expense_table ORDER BY date DESC")
    fun observeAllExpenses(): LiveData<List<Expense>>


    @Query("SELECT * FROM expense_table ORDER BY date DESC")
    suspend fun getAllExpenses(): List<Expense>

    @Query("SELECT * FROM expense_table WHERE type = :type ORDER BY date DESC")
    suspend fun getExpensesByType(type: String): List<Expense>

    @Query("""
    SELECT category, SUM(amount) as total
    FROM expense_table
    WHERE type = 'EXPENSE'
    GROUP BY category
""")
    suspend fun getCategoryTotals(): List<CategoryTotal>

}
