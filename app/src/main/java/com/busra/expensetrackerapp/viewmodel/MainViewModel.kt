package com.busra.expensetrackerapp.viewmodel

// veritabanındaki tüm harcama kayıtlarını al
// ekranda göster

// activitycontext -> memory leak

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.busra.expensetrackerapp.data.database.ExpenseDatabase
import com.busra.expensetrackerapp.data.entity.Expense

class MainViewModel (application: Application) : AndroidViewModel(application)


{

    private val expenseDao =
        ExpenseDatabase.getDatabase(application).expenseDao()

    val expenseList : LiveData<List<Expense>> =
        expenseDao.getAllExpenses()


}