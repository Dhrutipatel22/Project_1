package com.dhruti.project_1

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefs = requireContext().getSharedPreferences("questify_prefs", Context.MODE_PRIVATE)
        val name = prefs.getString("user_name", "Alex Kumar")
        val email = prefs.getString("user_email", "alex@example.com")
        val level = prefs.getInt("level", 1)
        val totalXp = prefs.getInt("total_xp", 0)
        val streak = prefs.getInt("streak", 0)

        view.findViewById<TextView>(R.id.txtProfileName).text = name
        view.findViewById<TextView>(R.id.txtProfileEmail).text = email
        view.findViewById<TextView>(R.id.txtProfileLevel).text = "LEVEL $level"
        view.findViewById<TextView>(R.id.txtProfileXp).text = "$totalXp XP"
        view.findViewById<TextView>(R.id.txtProfileStreak).text = "🔥 $streak Day Streak"

        // Placeholder taps for now — real screens can be added later if needed
        view.findViewById<TextView>(R.id.rowEditProfile).setOnClickListener {
            Toast.makeText(requireContext(), "Edit Profile — coming soon", Toast.LENGTH_SHORT).show()
        }
        view.findViewById<TextView>(R.id.rowNotifications).setOnClickListener {
            Toast.makeText(requireContext(), "Notifications — coming soon", Toast.LENGTH_SHORT).show()
        }
        view.findViewById<TextView>(R.id.rowAppearance).setOnClickListener {
            Toast.makeText(requireContext(), "Appearance — coming soon", Toast.LENGTH_SHORT).show()
        }
        view.findViewById<TextView>(R.id.rowAbout).setOnClickListener {
            Toast.makeText(requireContext(), "Questify v1.0", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        // Refresh in case XP/level changed elsewhere
        view?.let { onViewCreated(it, null) }
    }
}