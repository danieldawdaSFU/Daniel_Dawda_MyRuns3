package com.example.daniel_dawda_myruns3

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.daniel_dawda_myruns3.database.Activity

class ManualActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manual)

        val dateInput = findViewById<TextView>(R.id.date)
        dateInput.setOnClickListener {
            DatePickerFragment().show(supportFragmentManager, "datePicker")
        }

        val timeInput = findViewById<TextView>(R.id.time)
        timeInput.setOnClickListener {
            TimePickerFragment().show(supportFragmentManager, "timePicker")
        }

        val durationInput = findViewById<TextView>(R.id.duration)
        durationInput.setOnClickListener {
            val myDialog = ManualInputsDialogs()
            val bundle = Bundle()
            bundle.putInt(ManualInputsDialogs.DIALOG_KEY, ManualInputsDialogs.DUR_DIALOG)
            myDialog.arguments = bundle
            myDialog.show(supportFragmentManager, "manual dialog")
        }

        val distanceInput = findViewById<TextView>(R.id.distance)
        distanceInput.setOnClickListener {
            val myDialog = ManualInputsDialogs()
            val bundle = Bundle()
            bundle.putInt(ManualInputsDialogs.DIALOG_KEY, ManualInputsDialogs.DIST_DIALOG)
            myDialog.arguments = bundle
            myDialog.show(supportFragmentManager, "manual dialog")
        }

        val caloriesInput = findViewById<TextView>(R.id.calories)
        caloriesInput.setOnClickListener {
            val myDialog = ManualInputsDialogs()
            val bundle = Bundle()
            bundle.putInt(ManualInputsDialogs.DIALOG_KEY, ManualInputsDialogs.CAL_DIALOG)
            myDialog.arguments = bundle
            myDialog.show(supportFragmentManager, "manual dialog")
        }

        val hrInput = findViewById<TextView>(R.id.heart)
        hrInput.setOnClickListener {
            val myDialog = ManualInputsDialogs()
            val bundle = Bundle()
            bundle.putInt(ManualInputsDialogs.DIALOG_KEY, ManualInputsDialogs.HR_DIALOG)
            myDialog.arguments = bundle
            myDialog.show(supportFragmentManager, "manual dialog")
        }

        val commentInput = findViewById<TextView>(R.id.start_comment)
        commentInput.setOnClickListener {
            val myDialog = ManualInputsDialogs()
            val bundle = Bundle()
            bundle.putInt(ManualInputsDialogs.DIALOG_KEY, ManualInputsDialogs.COMM_DIALOG)
            myDialog.arguments = bundle
            myDialog.show(supportFragmentManager, "manual dialog")
        }

        val startButton = findViewById<Button>(R.id.start_manual)
        startButton.setOnClickListener {
            Toast.makeText(this, "Activity Saved", Toast.LENGTH_LONG).show()
            // TODO: implement start button and save preferences in database

            // open preferences
            val prefs = getSharedPreferences(Util.manualPreferences, MODE_PRIVATE)

            var activityInfo = Activity()
            activityInfo.activityType = 0
            activityInfo.duration = Util.getDouble(Util.durKey, prefs)
            activityInfo.distance = Util.getDouble(Util.distKey, prefs)
            activityInfo.duration = Util.getDouble(Util.durKey, prefs)
            activityInfo.distance = Util.getDouble(Util.distKey, prefs)




        }

        val cancelButton = findViewById<Button>(R.id.cancel_manual)
        cancelButton.setOnClickListener {
            Toast.makeText(this, "Cancel button clicked", Toast.LENGTH_LONG).show()
            val prefs = getSharedPreferences(Util.manualPreferences, MODE_PRIVATE)
            prefs.edit().clear().apply()
            finish()
            // TODO: implement cancel button
        }
    }
}