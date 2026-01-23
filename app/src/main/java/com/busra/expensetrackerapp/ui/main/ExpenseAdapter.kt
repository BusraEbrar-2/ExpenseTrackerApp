import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.busra.expensetrackerapp.R
import com.busra.expensetrackerapp.data.entity.Expense
import java.text.SimpleDateFormat
import java.util.*

class ExpenseAdapter : RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {

    private var expenseList = emptyList<Expense>()

    fun setData(list: List<Expense>) {
        expenseList = list
        notifyDataSetChanged()
    }

    class ExpenseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val category: TextView = itemView.findViewById(R.id.tvCategory)
        val amount: TextView = itemView.findViewById(R.id.tvAmount)
        val date: TextView = itemView.findViewById(R.id.tvDate)
        val icon: ImageView = itemView.findViewById(R.id.imgCategory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_expense, parent, false)
        return ExpenseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val current = expenseList[position]

        holder.category.text = current.category

        val sdf = SimpleDateFormat("dd MMM", Locale.getDefault())
        holder.date.text = sdf.format(Date(current.date))

        // 🔹 KATEGORİ İKONU
        holder.icon.setImageResource(
            when (current.category) {
                "Food" -> R.drawable.ic_food
                "Transport" -> R.drawable.ic_transport
                "Shopping" -> R.drawable.ic_shopping
                else -> R.drawable.ic_category
            }
        )

        if (current.type == "EXPENSE") {
            holder.amount.text = "-₺${current.amount}"
            holder.amount.setTextColor(Color.parseColor("#E53935"))
        } else {
            holder.amount.text = "+₺${current.amount}"
            holder.amount.setTextColor(Color.parseColor("#43A047"))
        }
    }


    override fun getItemCount() = expenseList.size
}
