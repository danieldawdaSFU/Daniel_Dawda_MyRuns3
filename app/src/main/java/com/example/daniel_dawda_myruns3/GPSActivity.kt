package com.example.daniel_dawda_myruns3

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class GPSActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gps)

        val saveButton = findViewById<Button>(R.id.gps_save)
        val cancelButton = findViewById<Button>(R.id.gps_cancel)

        saveButton.setOnClickListener() {
            //TODO: Implement gps save button
            finish()
        }

        cancelButton.setOnClickListener() {
            //TODO: Implement gps cancel button
            finish()
        }
    }

}