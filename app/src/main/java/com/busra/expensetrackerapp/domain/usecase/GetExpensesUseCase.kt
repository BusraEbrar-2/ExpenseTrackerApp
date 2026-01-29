package com.busra.expensetrackerapp.domain.usecase

import com.busra.expensetrackerapp.data.entity.Expense
import com.busra.expensetrackerapp.data.repository.ExpenseRepository

class GetExpensesUseCase(
    private val repository: ExpenseRepository
) {
    suspend operator fun invoke(): List<Expense> {
        return repository.getExpensesOnce()
    }
}
