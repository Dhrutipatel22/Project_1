package com.dhruti.project_1

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RewardsFragment : Fragment(R.layout.fragment_rewards) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refresh(view)
    }

    override fun onResume() {
        super.onResume()
        view?.let { refresh(it) }
    }

    private fun refresh(view: View) {
        val prefs = requireContext().getSharedPreferences("questify_prefs", Context.MODE_PRIVATE)
        val level = prefs.getInt("level", 1)
        val totalXp = prefs.getInt("total_xp", 0)

        view.findViewById<TextView>(R.id.txtRewardsLevel).text = "Level $level"
        view.findViewById<TextView>(R.id.txtRewardsXp).text = "$totalXp XP"

        val badges = BadgeStorage.getAllWithStatus(requireContext())
        val unlockedCount = badges.count { it.unlocked }
        view.findViewById<TextView>(R.id.txtUnlockedCount).text =
            "Unlocked ($unlockedCount)   Locked (${badges.size - unlockedCount})"

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerBadges)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        recyclerView.adapter = BadgeAdapter(badges)
    }
}