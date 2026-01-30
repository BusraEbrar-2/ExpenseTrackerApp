package com.busra.expensetrackerapp

import ExpenseAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.busra.expensetrackerapp.data.database.ExpenseDatabase
import com.busra.expensetrackerapp.data.entity.CategoryTotal
import com.busra.expensetrackerapp.data.repository.ExpenseRepository
import com.busra.expensetrackerapp.domain.usecase.GetCategoryTotalsUseCase
import com.busra.expensetrackerapp.domain.usecase.GetExpensesByTypeUseCase
import com.busra.expensetrackerapp.domain.usecase.GetExpensesUseCase
import com.busra.expensetrackerapp.ui.add.AddExpenseActivity
import com.busra.expensetrackerapp.ui.main.CategoryTotalAdapter
import com.busra.expensetrackerapp.util.Resource
import com.busra.expensetrackerapp.viewmodel.MainViewModel
import com.busra.expensetrackerapp.viewmodel.MainViewModelFactory
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ExpenseAdapter
    private lateinit var categoryAdapter: CategoryTotalAdapter

    private lateinit var btnAll: MaterialButton
    private lateinit var btnIncome: MaterialButton
    private lateinit var btnExpense: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 🔹 Expenses RecyclerView (VERTICAL)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        adapter = ExpenseAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // 🔹 Category Totals RecyclerView (HORIZONTAL)
        val rvCategoryTotals = findViewById<RecyclerView>(R.id.rvCategoryTotals)
        categoryAdapter = CategoryTotalAdapter()
        rvCategoryTotals.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvCategoryTotals.adapter = categoryAdapter

        val tvBalance = findViewById<TextView>(R.id.tvBalance)
        val tvEmpty = findViewById<TextView>(R.id.tvEmpty)

        // 🔘 Filter Buttons
        btnAll = findViewById(R.id.btnAll)
        btnIncome = findViewById(R.id.btnIncome)
        btnExpense = findViewById(R.id.btnExpense)

        // 🔗 Dependency chain
        val dao = ExpenseDatabase.getDatabase(this).expenseDao()
        val repository = ExpenseRepository(dao)

        val factory = MainViewModelFactory(
            GetExpensesUseCase(repository),
            GetExpensesByTypeUseCase(repository),
            GetCategoryTotalsUseCase(repository)
        )

        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        // 👀 Expenses Observer
        viewModel.expenses.observe(this) { result ->
            when (result) {

                is Resource.Loading -> {
                    tvEmpty.visibility = View.GONE
                    recyclerView.visibility = View.GONE
                }

                is Resource.Success -> {
                    val list = result.data
                    adapter.setData(list)

                    // 💰 Balance
                    val balance = list.sumOf {
                        if (it.type == "INCOME") it.amount else -it.amount
                    }
                    tvBalance.text = "₺$balance"

                    // 📊 Category totals (EXPENSE)
                    val categoryTotals = list
                        .filter { it.type == "EXPENSE" }
                        .groupBy { it.category }
                        .map {
                            CategoryTotal(
                                category = it.key,
                                total = it.value.sumOf { e -> e.amount }
                            )
                        }

                    categoryAdapter.setData(categoryTotals)

                    tvEmpty.visibility =
                        if (list.isEmpty()) View.VISIBLE else View.GONE
                    recyclerView.visibility = View.VISIBLE
                }

                is Resource.Error -> {
                    tvEmpty.text = result.message
                    tvEmpty.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
            }
        }

        // 🔹 Default state
        selectButton(btnAll)
        viewModel.loadExpenses()

        // 🔘 Button Clicks
        btnAll.setOnClickListener {
            selectButton(btnAll)
            viewModel.loadExpenses()
        }

        btnIncome.setOnClickListener {
            selectButton(btnIncome)
            viewModel.loadExpensesByType("INCOME")
        }

        btnExpense.setOnClickListener {
            selectButton(btnExpense)
            viewModel.loadExpensesByType("EXPENSE")
        }

        // ➕ Add Expense
        findViewById<FloatingActionButton>(R.id.fabAdd).setOnClickListener {
            startActivity(Intent(this, AddExpenseActivity::class.java))
        }
    }

    // 🎨 Selected button UI
    private fun selectButton(selected: MaterialButton) {
        val buttons = listOf(btnAll, btnIncome, btnExpense)

        buttons.forEach {
            it.isChecked = false
            it.setBackgroundColor(
                ContextCompat.getColor(this, android.R.color.transparent)
            )
            it.setTextColor(
                ContextCompat.getColor(this, R.color.purple_500)
            )
        }

        selected.isChecked = true
        selected.setBackgroundColor(
            ContextCompat.getColor(this, R.color.purple_500)
        )
        selected.setTextColor(
            ContextCompat.getColor(this, android.R.color.white)
        )
    }
}
