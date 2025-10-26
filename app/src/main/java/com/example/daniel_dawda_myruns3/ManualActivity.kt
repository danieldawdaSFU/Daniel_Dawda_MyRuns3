package com.example.daniel_dawda_myruns3

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.daniel_dawda_myruns3.database.Activity
import com.example.daniel_dawda_myruns3.database.ActivityViewModel
import kotlinx.serialization.builtins.ArraySerializer
import java.util.Calendar

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

            // open preferences
            val prefs = getSharedPreferences(Util.manualPreferences, MODE_PRIVATE)

            var activityInfo = Activity()
            activityInfo.inputType = prefs.getInt(Util.inputKey, 0)
            activityInfo.activityType = prefs.getInt(Util.actKey, 0)

            // turn date long into Calendar object
            val date = prefs.getLong(Util.dateKey, 0L)
            val (year, month, day) = Util.parseDate(date)
            val time = prefs.getLong(Util.timeKey, 0L)
            val (hour, minute) = Util.parseTime(time)
            val calendar = Calendar.getInstance()
            calendar.set(year, month, day, hour, minute)
            activityInfo.dateTime = calendar

            activityInfo.duration = Util.getDouble(Util.durKey, prefs)
            activityInfo.distance = Util.getDouble(Util.distKey, prefs)
            activityInfo.avgPace = 0.0
            activityInfo.avgSpeed = 0.0
            activityInfo.calorie = Util.getDouble(Util.calKey, prefs)
            activityInfo.climb = 0.0
            activityInfo.heartRate = Util.getDouble(Util.hrKey, prefs)
            activityInfo.comment = prefs.getString(Util.commKey, "").toString()
            activityInfo.locationList = ArrayList()

            // actually save into database
            val viewModelFactory = Util.initDatabase(this)
            val activityViewModel = ViewModelProvider(this, viewModelFactory).get(ActivityViewModel::class.java)
            activityViewModel.insert(activityInfo)

            activityViewModel.allActivitiesLiveData.observe(this) { activityList ->
                Log.d("DB_CHECK", "Number of activities in DB: ${activityList.size}")
                activityList.forEach {
                    Log.d("DB_CHECK", "Activity: ${it.activityType}, id: ${it.id}, duration: ${it.duration}")
                }
            }

            Toast.makeText(this, "Activity Saved", Toast.LENGTH_LONG).show()
            prefs.edit().clear().apply()
            finish()
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