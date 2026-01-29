/* package com.busra.expensetrackerapp.viewmodel

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
                val data = expenseDao.getAllExpenses()
                _expenses.value = Resource.Success(data)
            } catch (e: Exception) {
                _expenses.value = Resource.Error("Veriler yüklenemedi")
            }
        }
    }

} */
package com.busra.expensetrackerapp.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.busra.expensetrackerapp.data.entity.CategoryTotal
import com.busra.expensetrackerapp.data.entity.Expense
import com.busra.expensetrackerapp.domain.usecase.GetCategoryTotalsUseCase
import com.busra.expensetrackerapp.domain.usecase.GetExpensesUseCase
import com.busra.expensetrackerapp.util.Resource
import kotlinx.coroutines.launch
import com.busra.expensetrackerapp.domain.usecase.GetExpensesByTypeUseCase

class MainViewModel(
    private val getExpensesUseCase: GetExpensesUseCase ,
    private val getExpensesByTypeUseCase: GetExpensesByTypeUseCase,
    private val getCategoryTotalsUseCase: GetCategoryTotalsUseCase

) : ViewModel() {

    private val _expenses =
        MutableLiveData<Resource<List<Expense>>>()

    val expenses: LiveData<Resource<List<Expense>>> = _expenses

    fun loadExpenses() {
               _expenses.value = Resource.Loading()

        viewModelScope.launch {
            try {
                val data = getExpensesUseCase()
                _expenses.value = Resource.Success(data)


            } catch (e: Exception) {
                _expenses.value = Resource.Error("Veriler yüklenemedi")


            }
        }
    }

    fun loadExpensesByType(type: String) {
        _expenses.value = Resource.Loading()

        viewModelScope.launch {
            try {
                val data = getExpensesByTypeUseCase(type)

                _expenses.value = Resource.Success(data)
            } catch (e: Exception) {
                _expenses.value = Resource.Error("Filtreleme başarısız")
            }
        }
    }
    private val _categoryTotals = MutableLiveData<List<CategoryTotal>>()
    val categoryTotals: LiveData<List<CategoryTotal>> = _categoryTotals

    fun loadCategoryTotals() {
        viewModelScope.launch {
            _categoryTotals.value = getCategoryTotalsUseCase()
        }
    }


}
