package com.dhruti.project_1

import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class QuestCompletedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quest_completed)

        val xpEarned = intent.getIntExtra("xpEarned", 0)
        val leveledUp = intent.getBooleanExtra("leveledUp", false)
        val newLevel = intent.getIntExtra("newLevel", 1)
        val xpInLevel = intent.getIntExtra("xpInLevel", 0)
        val xpNeeded = intent.getIntExtra("xpNeeded", 200)
        val unlockedBadgeName = intent.getStringExtra("unlockedBadgeName")

        findViewById<TextView>(R.id.txtXpEarned).text = "+$xpEarned XP"
        findViewById<TextView>(R.id.txtXpProgress).text = "$xpInLevel / $xpNeeded XP"

        val progress = findViewById<ProgressBar>(R.id.progressCompleted)
        progress.max = xpNeeded
        progress.progress = xpInLevel

        if (leveledUp) {
            findViewById<android.view.View>(R.id.cardLevelUp).visibility = android.view.View.VISIBLE
            findViewById<TextView>(R.id.txtLevelUpMessage).text = "🎖️ Level Up! You reached Level $newLevel"
        }

        if (unlockedBadgeName != null) {
            findViewById<android.view.View>(R.id.cardBadgeUnlocked).visibility = android.view.View.VISIBLE
            findViewById<TextView>(R.id.txtBadgeUnlockedMessage).text = "🏆 New Badge Unlocked! $unlockedBadgeName"
        }

        findViewById<Button>(R.id.btnAwesome).setOnClickListener { finish() }
    }
}