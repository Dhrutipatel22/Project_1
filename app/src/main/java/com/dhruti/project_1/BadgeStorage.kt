package com.dhruti.project_1

import android.content.Context

object BadgeStorage {

    private const val PREFS = "questify_prefs"
    private const val KEY_UNLOCKED = "unlocked_badges"

    private fun getUnlockedIds(context: Context): MutableSet<String> {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        return prefs.getStringSet(KEY_UNLOCKED, emptySet())?.toMutableSet() ?: mutableSetOf()
    }

    private fun saveUnlockedIds(context: Context, ids: Set<String>) {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        prefs.edit().putStringSet(KEY_UNLOCKED, ids).apply()
    }

    /** Call this after any quest completion. Returns newly unlocked badges (for a toast/celebration). */
    fun checkAndUnlock(context: Context): List<Badge> {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val totalXp = prefs.getInt("total_xp", 0)
        val level = prefs.getInt("level", 1)
        val streak = prefs.getInt("streak", 0)
        val longestStreak = prefs.getInt("longest_streak", 0)
        val completedCount = QuestStorage.getQuests(context).count { it.isCompleted }

        // track longest streak separately for the "perfect_week" style badge
        if (streak > longestStreak) {
            prefs.edit().putInt("longest_streak", streak).apply()
        }
        val effectiveLongest = maxOf(streak, longestStreak)

        val unlocked = getUnlockedIds(context)
        val newlyUnlocked = mutableListOf<Badge>()

        fun tryUnlock(id: String, condition: Boolean) {
            if (condition && !unlocked.contains(id)) {
                unlocked.add(id)
                allBadges.find { it.id == id }?.let { newlyUnlocked.add(it) }
            }
        }

        tryUnlock("first_quest", completedCount >= 1)
        tryUnlock("streak_7", streak >= 7)
        tryUnlock("quest_master", completedCount >= 50)
        tryUnlock("xp_hunter", totalXp >= 1000)
        tryUnlock("level_5", level >= 5)
        tryUnlock("perfect_week", effectiveLongest >= 7 && completedCount >= 7)

        saveUnlockedIds(context, unlocked)
        return newlyUnlocked
    }

    fun getAllWithStatus(context: Context): List<BadgeUiModel> {
        val unlocked = getUnlockedIds(context)
        return allBadges.map { BadgeUiModel(it, unlocked.contains(it.id)) }
    }
}