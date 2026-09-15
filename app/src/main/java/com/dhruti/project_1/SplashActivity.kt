package com.dhruti.project_1

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val prefs = getSharedPreferences("questify_prefs", MODE_PRIVATE)
        val onboardingComplete = false

        Handler(Looper.getMainLooper()).postDelayed({
            val destination = if (onboardingComplete) {
                HomeActivity::class.java
            } else {
                OnboardingActivity::class.java
            }
            startActivity(Intent(this, destination))
            finish()
        }, 1500)
    }
}