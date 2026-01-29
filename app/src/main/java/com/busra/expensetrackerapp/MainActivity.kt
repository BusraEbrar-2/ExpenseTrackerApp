package com.busra.expensetrackerapp

import ExpenseAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
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
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ExpenseAdapter
    private lateinit var categoryAdapter: CategoryTotalAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 🔹 Expenses RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        adapter = ExpenseAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // 🔹 Category Totals RecyclerView
        val rvCategoryTotals = findViewById<RecyclerView>(R.id.rvCategoryTotals)
        categoryAdapter = CategoryTotalAdapter()
        rvCategoryTotals.layoutManager = LinearLayoutManager(this)
        rvCategoryTotals.adapter = categoryAdapter
        rvCategoryTotals.isNestedScrollingEnabled = false

        val tvBalance = findViewById<TextView>(R.id.tvBalance)
        val tvEmpty = findViewById<TextView>(R.id.tvEmpty)

        // 🔗 DEPENDENCY CHAIN
        val dao = ExpenseDatabase.getDatabase(this).expenseDao()
        val repository = ExpenseRepository(dao)

        val getExpensesUseCase = GetExpensesUseCase(repository)
        val getExpensesByTypeUseCase = GetExpensesByTypeUseCase(repository)
        val getCategoryTotalsUseCase = GetCategoryTotalsUseCase(repository)

        val factory = MainViewModelFactory(
            getExpensesUseCase,
            getExpensesByTypeUseCase,
            getCategoryTotalsUseCase
        )

        viewModel = ViewModelProvider(this, factory)
            .get(MainViewModel::class.java)

        // 👀 EXPENSES OBSERVER
        viewModel.expenses.observe(this) { result ->
            when (result) {

                is Resource.Loading -> {
                    tvEmpty.visibility = View.GONE
                    recyclerView.visibility = View.GONE
                }

                is Resource.Success -> {
                    val list = result.data

                    // Expenses
                    adapter.setData(list)

                    // Balance
                    val balance = list.sumOf {
                        if (it.type == "INCOME") it.amount else -it.amount
                    }
                    tvBalance.text = "₺$balance"

                    // Category totals (EXPENSE only)
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

        // 🔘 Filter Toggle
        val toggleGroup = findViewById<MaterialButtonToggleGroup>(R.id.filterToggleGroup)
        toggleGroup.check(R.id.btnAll)
        viewModel.loadExpenses()

        toggleGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (!isChecked) return@addOnButtonCheckedListener

            when (checkedId) {
                R.id.btnAll -> viewModel.loadExpenses()
                R.id.btnIncome -> viewModel.loadExpensesByType("INCOME")
                R.id.btnExpense -> viewModel.loadExpensesByType("EXPENSE")
            }
        }

        // ➕ Add Expense
        findViewById<FloatingActionButton>(R.id.fabAdd).setOnClickListener {
            startActivity(Intent(this, AddExpenseActivity::class.java))
        }
    }
}
