package com.example.daniel_dawda_myruns3

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

// adapted from Actiontabs demo
class StartFragment:  Fragment(), AdapterView.OnItemSelectedListener {
    lateinit var inputSpinner: Spinner
    lateinit var activitySpinner: Spinner
    lateinit var startButton: Button
    // 0 = GPS, 1 = Manual, 2 = Automatic
    var input_type: Int = 0
    // 0 = Running, 1 = Walking, etc
    var activity_type: Int = 0

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.start_fragment, container, false)

        inputSpinner = view.findViewById(R.id.input_spinner)
        activitySpinner = view.findViewById(R.id.activity_spinner)
        startButton = view.findViewById(R.id.start_button)

        inputSpinner.setSelection(0)
        activitySpinner.setSelection(0)

        inputSpinner.onItemSelectedListener = this
        activitySpinner.onItemSelectedListener = this

        startButton.setOnClickListener {
            val intent: Intent

            // save activity type and input type to shared preferences
            val sharedPreferences = requireContext().getSharedPreferences(Util.manualPreferences, MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putInt(Util.actKey, activity_type)
            editor.putInt(Util.inputKey, input_type)
            editor.apply()

            when (input_type) {
                0 -> {
                    intent = Intent(requireContext(), GPSActivity::class.java)
                    startActivity(intent)
                }

                1 -> {
                    intent = Intent(requireContext(), ManualActivity::class.java)
                    startActivity(intent)
                }

                2 -> {
                    intent = Intent(requireContext(), AutomaticActivity::class.java)
                    startActivity(intent)
                }
            }
        }

        return view
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when(parent?.id) {
            R.id.input_spinner -> {
                input_type = position
            }
            R.id.activity_spinner -> {
                activity_type = position
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        Toast.makeText(context, "Nothing selected", Toast.LENGTH_SHORT).show()
    }
}