package com.example.daniel_dawda_myruns3.History

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.daniel_dawda_myruns3.R
import com.example.daniel_dawda_myruns3.Util
import com.example.daniel_dawda_myruns3.Util.getViewModelFactory
import com.example.daniel_dawda_myruns3.database.ActivityViewModel
import kotlinx.coroutines.launch

class HistoryItemActivity: AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_item)


        val itemId = intent.getLongExtra("itemId", -1L)
        if (itemId == -1L) {
            Toast.makeText(this, "Invalid item ID", Toast.LENGTH_SHORT).show()
            finish()
        }

        Log.d("test", "Item ID: $itemId")

        // Initialize the views

        val inputType = findViewById<TextView>(R.id.input_item)
        val activityType = findViewById<TextView>(R.id.activity_item)
        val datetime = findViewById<TextView>(R.id.datetime_item)
        val duration = findViewById<TextView>(R.id.duration_item)
        val distance = findViewById<TextView>(R.id.distance_item)
        val calories = findViewById<TextView>(R.id.calories_item)
        val heartRate = findViewById<TextView>(R.id.hr_item)
        val delete = findViewById<Button>(R.id.delete_button)

        // Retrieve the item data from the database
        val viewModelFactory = getViewModelFactory(this)
        val activityViewModel =
            ViewModelProvider(this, viewModelFactory).get(ActivityViewModel::class.java)

        // set the data in the views
        // lifescycleScope stuff adapted from ChatGPT
        lifecycleScope.launch {
            val activity = activityViewModel.getActivity(itemId)
            if (activity != null) {
                inputType.text = "${Util.inputMap[activity.inputType]} Input"
                activityType.text = Util.activityMap[activity.activityType]
                datetime.text = Util.calendarToString(activity.dateTime)

                // duration conversion
                val (minutes, seconds) = Util.toMinutesAndSeconds(activity.duration)

                duration.text = "${minutes}mins ${seconds}secs"

                // distance conversion
                // retrieve unit preference
                var unitChecked: Int
                val settingsPreferences = getSharedPreferences(Util.settingsPrefKey, MODE_PRIVATE)
                unitChecked = settingsPreferences.getInt(Util.unitKey, 0)

                if (unitChecked == 0) {
                    distance.text = "${activity.distance} km"
                } else {
                    val miles = Util.kilometersToMiles(activity.distance)
                    val rounded = String.format("%.2f", miles)
                    distance.text = "${rounded} miles"
                }

                calories.text = "${activity.calorie} cals"
                heartRate.text = "${activity.heartRate} bpm"

            } else {
                Toast.makeText(this@HistoryItemActivity, "Activity not found", Toast.LENGTH_SHORT).show()
                finish()
            }

            // delete selected item
            delete.setOnClickListener() {
                activityViewModel.delete(itemId)
                Toast.makeText(this@HistoryItemActivity, "Activity ${itemId} Deleted", Toast.LENGTH_SHORT).show()
                finish()
            }


        }
    }
}