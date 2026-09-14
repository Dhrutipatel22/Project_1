package com.dhruti.project_1

data class Badge(
    val id: String,
    val name: String,
    val description: String
)

val allBadges = listOf(
    Badge("first_quest", "First Quest", "Complete your first quest"),
    Badge("streak_7", "7 Day Warrior", "Maintain a 7 day streak"),
    Badge("quest_master", "Quest Master", "Complete 50 quests"),
    Badge("xp_hunter", "XP Hunter", "Earn 1000 total XP"),
    Badge("level_5", "Level 5", "Reach level 5"),
    Badge("perfect_week", "Perfect Week", "Hit a 7 day streak, twice")
)

data class BadgeUiModel(val badge: Badge, val unlocked: Boolean)