package com.dhruti.project_1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AnalyticsFragment : Fragment(R.layout.fragment_analytics) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refresh(view)
    }

    override fun onResume() {
        super.onResume()
        view?.let { refresh(it) }
    }

    private fun refresh(view: View) {
        val quests = QuestStorage.getQuests(requireContext())
        val total = quests.size
        val completed = quests.count { it.isCompleted }
        val rate = if (total == 0) 0 else (completed * 100 / total)

        val emptyText = view.findViewById<TextView>(R.id.txtAnalyticsEmpty)
        emptyText.visibility = if (total == 0) View.VISIBLE else View.GONE

        view.findViewById<TextView>(R.id.txtTotalValue).text = "$total"
        view.findViewById<TextView>(R.id.txtCompletedValue).text = "$completed"
        view.findViewById<TextView>(R.id.txtRateValue).text = "$rate%"

        val byCategory = quests.groupBy { it.category }
        val stats = byCategory.map { entry ->
            val category = entry.key
            val list = entry.value
            CategoryStat(
                name = category,
                count = list.size,
                percent = if (total == 0) 0 else (list.size * 100 / total)
            )
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerCategories)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = CategoryAdapter(stats)
    }
}

data class CategoryStat(
    val name: String,
    val count: Int,
    val percent: Int
)

class CategoryAdapter(private val stats: List<CategoryStat>) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtName: TextView = view.findViewById(R.id.txtCategoryName)
        val txtCount: TextView = view.findViewById(R.id.txtCategoryCount)
        val progress: ProgressBar = view.findViewById(R.id.progressCategory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category_row, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val stat = stats[position]
        holder.txtName.text = stat.name
        holder.txtCount.text = "${stat.count} Quests"
        holder.progress.progress = stat.percent
    }

    override fun getItemCount() = stats.size
}
