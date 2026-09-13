package com.dhruti.project_1

data class OnboardingItem(
    val iconRes: Int,
    val title: String,
    val body: String
)

val onboardingPages = listOf(
    OnboardingItem(
        R.drawable.image1,
        "Turn Tasks Into Quests",
        "Transform everyday tasks into fun quests and make productivity more rewarding."
    ),
    OnboardingItem(
        R.drawable.image2,
        "Earn XP & Level Up",
        "Complete quests, earn XP, unlock achievements and build your productivity streak."
    ),
    OnboardingItem(
        R.drawable.image3,
        "Understand Your Productivity",
        "Track daily, weekly and monthly performance and discover your productivity patterns."
    )
)