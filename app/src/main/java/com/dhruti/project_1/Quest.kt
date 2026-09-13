package com.dhruti.project_1


data class Quest(
    val id: Long,
    val title: String,
    val description: String,
    val category: String,
    val priority: String,
    val xpReward: Int,
    var isCompleted: Boolean = false
)