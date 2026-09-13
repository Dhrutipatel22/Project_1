package com.dhruti.project_1


import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val prefs = getSharedPreferences("questify_prefs", MODE_PRIVATE)
        val name = prefs.getString("user_name", "there")

        findViewById<TextView>(R.id.txtWelcome).text = "Welcome, $name! \uD83C\uDF89"
    }
}