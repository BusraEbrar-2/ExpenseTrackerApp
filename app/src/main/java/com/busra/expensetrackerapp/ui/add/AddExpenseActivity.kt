package com.busra.expensetrackerapp.ui.add

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.busra.expensetrackerapp.R
import com.busra.expensetrackerapp.data.database.ExpenseDatabase
import com.busra.expensetrackerapp.data.entity.Expense
import com.busra.expensetrackerapp.data.repository.ExpenseRepository
import com.busra.expensetrackerapp.databinding.ActivityAddExpenseBinding
import com.busra.expensetrackerapp.domain.usecase.AddExpenseUseCase
import com.busra.expensetrackerapp.viewmodel.AddExpenseViewModel
import com.busra.expensetrackerapp.viewmodel.AddExpenseViewModelFactory

class AddExpenseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddExpenseBinding
    private lateinit var viewModel: AddExpenseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddExpenseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ---- ViewModel setup (DOĞRU YAPI) ----
        val dao = ExpenseDatabase.getDatabase(this).expenseDao()
        val repository = ExpenseRepository(dao)
        val addExpenseUseCase = AddExpenseUseCase(repository)
        val factory = AddExpenseViewModelFactory(addExpenseUseCase)

        viewModel = ViewModelProvider(this, factory)
            .get(AddExpenseViewModel::class.java)
        // -------------------------------------

        val categories = listOf("Food", "Transport", "Shopping", "Other")
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line,
            categories
        )
        binding.spinnerCategory.setAdapter(adapter)

        binding.toggleGroup.check(R.id.btnExpense)

        binding.btnSave.setOnClickListener {

            val amount = binding.etAmount.text.toString().toDoubleOrNull()
            if (amount == null || amount <= 0) {
                binding.etAmount.error = "Enter valid amount"
                return@setOnClickListener
            }

            val category = binding.spinnerCategory.text.toString()
            if (category.isBlank()) {
                binding.spinnerCategory.error = "Select category"
                return@setOnClickListener
            }

            val type =
                if (binding.toggleGroup.checkedButtonId == R.id.btnIncome)
                    "INCOME"
                else
                    "EXPENSE"

            val expense = Expense(
                amount = amount,
                category = category,
                date = System.currentTimeMillis(),
                type = type
            )

            viewModel.insertExpense(expense)
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
