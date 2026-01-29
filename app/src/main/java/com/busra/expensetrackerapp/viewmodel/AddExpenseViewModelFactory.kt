package com.busra.expensetrackerapp.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.busra.expensetrackerapp.domain.usecase.AddExpenseUseCase


class AddExpenseViewModelFactory(
    private val addExpenseUseCase: AddExpenseUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddExpenseViewModel::class.java)) {
            return AddExpenseViewModel(addExpenseUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
