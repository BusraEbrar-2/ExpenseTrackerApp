package com.busra.expensetrackerapp.domain.usecase


import com.busra.expensetrackerapp.data.entity.CategoryTotal
import com.busra.expensetrackerapp.data.repository.ExpenseRepository

class GetCategoryTotalsUseCase(
    private val repository: ExpenseRepository
) {
    suspend operator fun invoke(): List<CategoryTotal> {
        return repository.getCategoryTotals()
    }
}
