package com.dhruti.project_1

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class QuestsFragment : Fragment(R.layout.fragment_quests) {

    private enum class Filter { ALL, PENDING, COMPLETED }
    private var currentFilter = Filter.ALL

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnAll = view.findViewById<Button>(R.id.btnFilterAll)
        val btnPending = view.findViewById<Button>(R.id.btnFilterPending)
        val btnCompleted = view.findViewById<Button>(R.id.btnFilterCompleted)

        btnAll.setOnClickListener { currentFilter = Filter.ALL; refresh(view) }
        btnPending.setOnClickListener { currentFilter = Filter.PENDING; refresh(view) }
        btnCompleted.setOnClickListener { currentFilter = Filter.COMPLETED; refresh(view) }

        refresh(view)
    }

    override fun onResume() {
        super.onResume()
        view?.let { refresh(it) }
    }

    private fun refresh(view: View) {
        highlightActiveFilter(view)

        val allQuests = QuestStorage.getQuests(requireContext())
        val filtered = when (currentFilter) {
            Filter.ALL -> allQuests
            Filter.PENDING -> allQuests.filter { !it.isCompleted }
            Filter.COMPLETED -> allQuests.filter { it.isCompleted }
        }

        val emptyText = view.findViewById<TextView>(R.id.txtEmptyQuests)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerQuests)

        if (filtered.isEmpty()) {
            emptyText.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            emptyText.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = QuestAdapter(filtered) { quest ->
                QuestStorage.completeQuest(requireContext(), quest.id)
                Toast.makeText(requireContext(), "+${quest.xpReward} XP!", Toast.LENGTH_SHORT).show()
                refresh(view)
            }
        }
    }

    private fun highlightActiveFilter(view: View) {
        val btnAll = view.findViewById<Button>(R.id.btnFilterAll)
        val btnPending = view.findViewById<Button>(R.id.btnFilterPending)
        val btnCompleted = view.findViewById<Button>(R.id.btnFilterCompleted)

        val activeColor = android.graphics.Color.parseColor("#7C3AED")
        val inactiveColor = android.graphics.Color.parseColor("#EDE9FE")
        val activeText = android.graphics.Color.WHITE
        val inactiveText = android.graphics.Color.parseColor("#1E1B2E")

        btnAll.setBackgroundColor(if (currentFilter == Filter.ALL) activeColor else inactiveColor)
        btnAll.setTextColor(if (currentFilter == Filter.ALL) activeText else inactiveText)

        btnPending.setBackgroundColor(if (currentFilter == Filter.PENDING) activeColor else inactiveColor)
        btnPending.setTextColor(if (currentFilter == Filter.PENDING) activeText else inactiveText)

        btnCompleted.setBackgroundColor(if (currentFilter == Filter.COMPLETED) activeColor else inactiveColor)
        btnCompleted.setTextColor(if (currentFilter == Filter.COMPLETED) activeText else inactiveText)
    }
}