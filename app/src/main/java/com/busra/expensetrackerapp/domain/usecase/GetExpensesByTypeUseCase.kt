package com.busra.expensetrackerapp.domain.usecase

import com.busra.expensetrackerapp.data.repository.ExpenseRepository

class GetExpensesByTypeUseCase(
    private val repository: ExpenseRepository
) {
    suspend operator fun invoke(type: String) =
        repository.getExpensesByType(type)
}
