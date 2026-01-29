package com.busra.expensetrackerapp.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.busra.expensetrackerapp.R
import com.busra.expensetrackerapp.data.entity.CategoryTotal

class CategoryTotalAdapter :
    RecyclerView.Adapter<CategoryTotalAdapter.ViewHolder>() {

    private val list = mutableListOf<CategoryTotal>()

    fun setData(newList: List<CategoryTotal>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvCategory = view.findViewById<TextView>(R.id.tvCategory)
        val tvTotal = view.findViewById<TextView>(R.id.tvTotal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category_total, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.tvCategory.text = item.category
        holder.tvTotal.text = "₺${item.total}"
    }

    override fun getItemCount() = list.size
}
