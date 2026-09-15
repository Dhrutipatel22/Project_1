package com.dhruti.project_1


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class UserSetupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_setup)

        val editName = findViewById<EditText>(R.id.editName)
        val editEmail = findViewById<EditText>(R.id.editEmail)
        val btnStart = findViewById<Button>(R.id.btnStart)

        btnStart.setOnClickListener {
            val name = editName.text.toString().ifBlank { "xyz" }
            val email = editEmail.text.toString().ifBlank { "abc@example.com" }


            val prefs = getSharedPreferences("questify_prefs", MODE_PRIVATE)
            prefs.edit()
                .putString("user_name", name)
                .putString("user_email", email)
                .putBoolean("onboarding_complete", true)
                .apply()

            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }
}