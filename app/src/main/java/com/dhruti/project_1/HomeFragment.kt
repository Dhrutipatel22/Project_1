package com.dhruti.project_1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btnAddQuest).setOnClickListener {
            startActivity(Intent(requireContext(), AddQuestActivity::class.java))
        }

        refreshData(view)
    }

    override fun onResume() {
        super.onResume()
        view?.let { refreshData(it) }
    }

    private fun refreshData(view: View) {
        val prefs = requireContext().getSharedPreferences("questify_prefs", Context.MODE_PRIVATE)
        val name = prefs.getString("user_name", "there")
        val totalXp = prefs.getInt("total_xp", 0)
        val level = prefs.getInt("level", 1)
        val streak = prefs.getInt("streak", 0)
        val xpNeeded = QuestStorage.xpForLevel(level)

        view.findViewById<TextView>(R.id.txtGreeting).text = "Good Morning, $name 👋"
        view.findViewById<TextView>(R.id.txtLevel).text = "LEVEL $level"
        view.findViewById<TextView>(R.id.txtXp).text = "$totalXp / $xpNeeded XP"
        view.findViewById<TextView>(R.id.txtStreak).text = "🔥 $streak Day Streak"

        val progress = view.findViewById<ProgressBar>(R.id.progressXp)
        progress.max = xpNeeded
        progress.progress = totalXp

        val quests = QuestStorage.getQuests(requireContext()).filter { !it.isCompleted }
        val emptyState = view.findViewById<View>(R.id.emptyState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.questListContainer)

        if (quests.isEmpty()) {
            emptyState.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            emptyState.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = QuestAdapter(quests) { quest ->
                QuestStorage.completeQuest(requireContext(), quest.id)
                Toast.makeText(requireContext(), "+${quest.xpReward} XP!", Toast.LENGTH_SHORT).show()
                refreshData(view)
            }
        }
    }
}