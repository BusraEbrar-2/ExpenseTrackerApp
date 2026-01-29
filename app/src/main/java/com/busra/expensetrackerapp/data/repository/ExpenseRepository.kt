package com.busra.expensetrackerapp.data.repository

import com.busra.expensetrackerapp.data.entity.CategoryTotal
import androidx.lifecycle.LiveData
import com.busra.expensetrackerapp.data.dao.ExpenseDao
import com.busra.expensetrackerapp.data.entity.Expense

class ExpenseRepository(
    private val expenseDao: ExpenseDao
) {

    fun observeExpenses(): LiveData<List<Expense>> {
        return expenseDao.observeAllExpenses()
    }

    suspend fun getExpensesOnce(): List<Expense> {
        return expenseDao.getAllExpenses()
    }

    suspend fun addExpense(expense: Expense) {
        expenseDao.insertExpense(expense)
    }
    suspend fun getExpensesByType(type: String): List<Expense> {
        return expenseDao.getExpensesByType(type)
    }
    suspend fun getCategoryTotals(): List<CategoryTotal> {
        return expenseDao.getCategoryTotals()
    }

}
