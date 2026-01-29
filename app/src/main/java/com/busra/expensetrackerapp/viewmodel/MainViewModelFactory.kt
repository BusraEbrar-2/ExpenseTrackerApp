package com.busra.expensetrackerapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.busra.expensetrackerapp.domain.usecase.GetCategoryTotalsUseCase
import com.busra.expensetrackerapp.domain.usecase.GetExpensesByTypeUseCase
import com.busra.expensetrackerapp.domain.usecase.GetExpensesUseCase

class MainViewModelFactory(
    private val getExpensesUseCase: GetExpensesUseCase,
    private val getExpensesByTypeUseCase: GetExpensesByTypeUseCase,
    private val getCategoryTotalsUseCase: GetCategoryTotalsUseCase

) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(getExpensesUseCase, getExpensesByTypeUseCase,  getCategoryTotalsUseCase
            )
                    as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

    /*

2️⃣ ViewModel ile ne yapacaksın?

Database’den veri çekmek

viewModel.expenseList.observe(viewLifecycleOwner) { expenseList ->
    // expenseList UI’ya aktar
}


UI’yı güncellemek

RecyclerView adapter’ına verileri verirsin

Veri değişirse UI otomatik güncellenir

İş mantığını ViewModel’e taşımak

Örneğin, harcama ekleme, silme, filtreleme gibi işlemler

UI sadece “çizim” yapar, veri işleme ViewModel’de olur
     */

