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
import com.busra.expensetrackerapp.ui.add.AddExpenseActivity
import com.busra.expensetrackerapp.viewmodel.MainViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ExpenseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        adapter = ExpenseAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        val tvBalance = findViewById<TextView>(R.id.tvBalance)
        val tvEmpty = findViewById<TextView>(R.id.tvEmpty)

        viewModel.expenseList.observe(this) { list ->
            adapter.setData(list)

            val balance = list.sumOf {
                if (it.type == "INCOME") it.amount else -it.amount
            }
            tvBalance.text = "₺$balance"

            tvEmpty.visibility =
                if (list.isEmpty()) View.VISIBLE else View.GONE
        }

        findViewById<FloatingActionButton>(R.id.fabAdd).setOnClickListener {
            startActivity(Intent(this, AddExpenseActivity::class.java))
        }
    }
}
