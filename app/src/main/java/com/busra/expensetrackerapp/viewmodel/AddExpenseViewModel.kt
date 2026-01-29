package com.busra.expensetrackerapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.busra.expensetrackerapp.data.entity.Expense
import com.busra.expensetrackerapp.domain.usecase.AddExpenseUseCase
import kotlinx.coroutines.launch

class AddExpenseViewModel(
    private val addExpenseUseCase: AddExpenseUseCase
) : ViewModel() {

    fun insertExpense(expense: Expense) {
        viewModelScope.launch {
            addExpenseUseCase(expense)
        }
    }
}