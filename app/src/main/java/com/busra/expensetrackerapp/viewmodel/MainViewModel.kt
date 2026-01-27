package com.busra.expensetrackerapp.viewmodel

// veritabanındaki tüm harcama kayıtlarını al
// ekranda göster

// activitycontext -> memory leak

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.busra.expensetrackerapp.data.database.ExpenseDatabase
import com.busra.expensetrackerapp.data.entity.Expense
import com.busra.expensetrackerapp.util.Resource
import kotlinx.coroutines.launch

class MainViewModel (application: Application) : AndroidViewModel(application)


{

    private val expenseDao =
        ExpenseDatabase.getDatabase(application).expenseDao()

    private val _expenses =
        MutableLiveData<Resource<List<Expense>>>()

    val expenses: LiveData<Resource<List<Expense>>> = _expenses

    fun loadExpenses() {
        _expenses.value = Resource.Loading()

        viewModelScope.launch {
            try {
                val data = expenseDao.getAllExpensesOnce()
                _expenses.value = Resource.Success(data)
            } catch (e: Exception) {
                _expenses.value = Resource.Error("Veriler yüklenemedi")
            }
        }
    }

}