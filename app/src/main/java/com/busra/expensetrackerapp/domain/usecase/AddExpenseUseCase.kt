package com.busra.expensetrackerapp.domain.usecase

import com.busra.expensetrackerapp.data.entity.Expense
import com.busra.expensetrackerapp.data.repository.ExpenseRepository

class AddExpenseUseCase(
    private val repository: ExpenseRepository
) {
    suspend operator fun invoke(expense: Expense) {
        repository.addExpense(expense)
    }
}
