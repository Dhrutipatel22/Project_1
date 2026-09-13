package com.dhruti.project_1

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject
import java.util.Calendar

object QuestStorage {

    private const val PREFS = "questify_prefs"
    private const val KEY_QUESTS = "quests_json"

    fun getQuests(context: Context): MutableList<Quest> {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val jsonString = prefs.getString(KEY_QUESTS, "[]") ?: "[]"
        val array = JSONArray(jsonString)
        val list = mutableListOf<Quest>()
        for (i in 0 until array.length()) {
            val obj = array.getJSONObject(i)
            list.add(
                Quest(
                    id = obj.getLong("id"),
                    title = obj.getString("title"),
                    description = obj.optString("description", ""),
                    category = obj.optString("category", "Other"),
                    priority = obj.optString("priority", "Medium"),
                    xpReward = obj.optInt("xpReward", 20),
                    isCompleted = obj.optBoolean("isCompleted", false)
                )
            )
        }
        return list
    }

    private fun saveQuests(context: Context, quests: List<Quest>) {
        val array = JSONArray()
        quests.forEach { q ->
            val obj = JSONObject()
            obj.put("id", q.id)
            obj.put("title", q.title)
            obj.put("description", q.description)
            obj.put("category", q.category)
            obj.put("priority", q.priority)
            obj.put("xpReward", q.xpReward)
            obj.put("isCompleted", q.isCompleted)
            array.put(obj)
        }
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_QUESTS, array.toString()).apply()
    }

    fun addQuest(context: Context, quest: Quest) {
        val quests = getQuests(context)
        quests.add(quest)
        saveQuests(context, quests)
    }

    /** Marks the quest complete, awards XP, updates level + streak. Returns new total XP. */
    fun completeQuest(context: Context, questId: Long) {
        val quests = getQuests(context)
        val quest = quests.find { it.id == questId } ?: return
        if (quest.isCompleted) return
        quest.isCompleted = true
        saveQuests(context, quests)

        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val currentXp = prefs.getInt("total_xp", 0)
        val newXp = currentXp + quest.xpReward

        var level = 1
        var remaining = newXp
        while (remaining >= xpForLevel(level)) {
            remaining -= xpForLevel(level)
            level++
        }

        val today = dayStamp()
        val lastDay = prefs.getInt("last_active_day", -1)
        val streak = prefs.getInt("streak", 0)
        val newStreak = when {
            lastDay == -1 -> 1
            lastDay == today -> streak
            lastDay == today - 1 -> streak + 1
            else -> 1
        }

        prefs.edit()
            .putInt("total_xp", newXp)
            .putInt("level", level)
            .putInt("streak", newStreak)
            .putInt("last_active_day", today)
            .apply()
    }

    fun xpForLevel(level: Int): Int = 200 + (level - 1) * 150

    private fun dayStamp(): Int {
        val cal = Calendar.getInstance()
        return cal.get(Calendar.YEAR) * 1000 + cal.get(Calendar.DAY_OF_YEAR)
    }
}