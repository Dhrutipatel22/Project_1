package com.dhruti.project_1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2

class OnboardingActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var dotsLayout: LinearLayout
    private lateinit var btnNext: Button
    private lateinit var dots: Array<ImageView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        viewPager = findViewById(R.id.viewPager)
        dotsLayout = findViewById(R.id.dotsLayout)
        btnNext = findViewById(R.id.btnNext)

        viewPager.adapter = OnboardingAdapter(onboardingPages)
        setupDots(0)

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                setupDots(position)
                btnNext.text = if (position == onboardingPages.lastIndex) "Get Started" else "Next"
            }
        })

        btnNext.setOnClickListener {
            val next = viewPager.currentItem + 1
            if (next < onboardingPages.size) {
                viewPager.currentItem = next
            } else {
                startActivity(Intent(this, UserSetupActivity::class.java))
                finish()
            }
        }
    }

    private fun setupDots(activePosition: Int) {
        dotsLayout.removeAllViews()
        dots = Array(onboardingPages.size) { ImageView(this) }
        dots.forEachIndexed { index, dot ->
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(6, 0, 6, 0)
            dot.layoutParams = params
            dot.setImageResource(if (index == activePosition) R.drawable.dot_active else R.drawable.dot_inactive)
            dotsLayout.addView(dot)
        }
    }
}