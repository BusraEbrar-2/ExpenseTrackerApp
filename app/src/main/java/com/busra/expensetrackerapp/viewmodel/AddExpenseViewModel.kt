package com.busra.expensetrackerapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.busra.expensetrackerapp.data.database.ExpenseDatabase
import com.busra.expensetrackerapp.data.entity.Expense
import kotlinx.coroutines.launch


class AddExpenseViewModel (application: Application) :
AndroidViewModel(application)
{
    private val expenseDao=
        ExpenseDatabase.getDatabase(application).expenseDao()


    fun insertExpense (expense: Expense) {
        viewModelScope.launch{
            expenseDao.insertExpense(expense)
        }
    }
}