package com.dhruti.project_1

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddQuestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_quest)

        val editTitle = findViewById<EditText>(R.id.editTitle)
        val editDescription = findViewById<EditText>(R.id.editDescription)
        val spinnerCategory = findViewById<Spinner>(R.id.spinnerCategory)
        val spinnerPriority = findViewById<Spinner>(R.id.spinnerPriority)
        val spinnerXp = findViewById<Spinner>(R.id.spinnerXp)
        val btnCancel = findViewById<Button>(R.id.btnCancel)
        val btnCreate = findViewById<Button>(R.id.btnCreate)

        val categories = listOf("Study", "Work", "Health", "Personal", "Other")
        val priorities = listOf("Low", "Medium", "High")
        val xpOptions = listOf("10", "20", "30", "50")

        spinnerCategory.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categories)
        spinnerPriority.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, priorities)
        spinnerXp.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, xpOptions)
        spinnerPriority.setSelection(1) // default Medium

        btnCancel.setOnClickListener { finish() }

        btnCreate.setOnClickListener {
            val title = editTitle.text.toString().trim()
            if (title.isEmpty()) {
                Toast.makeText(this, "Please enter a quest title", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val quest = Quest(
                id = System.currentTimeMillis(),
                title = title,
                description = editDescription.text.toString().trim(),
                category = spinnerCategory.selectedItem.toString(),
                priority = spinnerPriority.selectedItem.toString(),
                xpReward = spinnerXp.selectedItem.toString().toInt()
            )

            QuestStorage.addQuest(this, quest)
            finish()
        }
    }
}