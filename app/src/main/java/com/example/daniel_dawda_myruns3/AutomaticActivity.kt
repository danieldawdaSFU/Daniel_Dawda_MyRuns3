package com.example.daniel_dawda_myruns3

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class AutomaticActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_automatic)

        val saveButton = findViewById<Button>(R.id.auto_save)
        val cancelButton = findViewById<Button>(R.id.auto_cancel)

        saveButton.setOnClickListener() {
            //TODO: Implement automatic save button
            finish()
        }

        cancelButton.setOnClickListener() {
            //TODO: Implement automatic cancel button
            finish()
        }
    }
}