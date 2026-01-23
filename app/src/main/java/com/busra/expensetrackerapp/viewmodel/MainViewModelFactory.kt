package com.busra.expensetrackerapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainViewModelFactory (
    private val application: Application

) : ViewModelProvider.Factory // viewmodel üreten sınıf
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(application) as T

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

