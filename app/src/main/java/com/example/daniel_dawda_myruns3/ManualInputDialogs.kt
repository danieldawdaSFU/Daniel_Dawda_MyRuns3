package com.example.daniel_dawda_myruns3

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context.MODE_PRIVATE
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputType
import android.text.format.DateFormat
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import java.util.Calendar

class ManualInputsDialogs: DialogFragment(), DialogInterface.OnClickListener {

    companion object {
        const val DIALOG_KEY = "manual dialog"
        const val DUR_DIALOG = 1
        const val DIST_DIALOG = 2
        const val CAL_DIALOG = 3
        const val HR_DIALOG = 4
        const val COMM_DIALOG = 5
    }

    lateinit var input: EditText
    lateinit var sharedPreferences: SharedPreferences
    private var dialogId: Int? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        lateinit var ret: Dialog
        val bundle = arguments
        dialogId = bundle?.getInt(DIALOG_KEY)
        val builder = AlertDialog.Builder(requireActivity())
        val view: View = requireActivity().layoutInflater.inflate(
            R.layout.basic_manual_input_fragment,
            null
        )

        input = view.findViewById(R.id.manual_input)

        sharedPreferences = requireContext().getSharedPreferences(Util.manualPreferences, MODE_PRIVATE)
        when (dialogId) {
            DUR_DIALOG -> {

                input.inputType = InputType.TYPE_CLASS_NUMBER

                // retrieve duration preference
                val dur: Double = Util.getDouble(Util.durKey, sharedPreferences)
                if (dur != 0.0) {
                    input.setText(dur.toString())
                }
                input.hint = "duration in minutes"


                builder.setView(view)
                builder.setTitle("Duration")
                builder.setPositiveButton("Confirm", this)
                builder.setNegativeButton("Cancel", this)
                ret = builder.create()

            }
            DIST_DIALOG -> {

                // retrieve unit preference
                val settingsPrefKey = "settingsKey"
                val unitKey = "unit"
                var unitChecked: Int
                val settingsPreferences =
                    requireContext().getSharedPreferences(settingsPrefKey, MODE_PRIVATE)
                unitChecked = settingsPreferences.getInt(unitKey, 0)

                // retrieve distance preference
                val dist: Double = Util.getDouble(Util.distKey, sharedPreferences)

                if (dist != 0.0) {
                    input.setText(dist.toString())
                }
                if (unitChecked == 0) {
                    input.hint = "distance in kilometers"
                } else {
                    input.hint = "distance in miles"
                }

                input.inputType = InputType.TYPE_CLASS_NUMBER

                builder.setView(view)
                builder.setTitle("Distance")
                builder.setPositiveButton("Confirm", this)
                builder.setNegativeButton("Cancel", this)
                ret = builder.create()

            }
            CAL_DIALOG -> {

                // retrieve calorie
                val cals: Double = Util.getDouble(Util.calKey, sharedPreferences)

                if (cals != 0.0) {
                    input.setText(cals.toString())
                }
                input.hint = "calories burned"
                input.inputType = InputType.TYPE_CLASS_NUMBER

                builder.setView(view)
                builder.setTitle("Calories")
                builder.setPositiveButton("Confirm", this)
                builder.setNegativeButton("Cancel", this)
                ret = builder.create()

            }
            HR_DIALOG -> {
                // retrieve heart rate
                val hr: Double = Util.getDouble(Util.hrKey, sharedPreferences)

                if (hr != 0.0) {
                    input.setText(hr.toString())
                }
                input.hint = "average heart rate"
                input.inputType = InputType.TYPE_CLASS_NUMBER

                builder.setView(view)
                builder.setTitle("Heart Rate")
                builder.setPositiveButton("Confirm", this)
                builder.setNegativeButton("Cancel", this)
                ret = builder.create()

            }
            COMM_DIALOG -> {
                // retrieve comment
                val comment: String = sharedPreferences.getString(Util.commKey, "").toString()

                if (comment != "") {
                    input.setText(comment)
                }
                input.hint = "additional comments"
                input.inputType = InputType.TYPE_CLASS_TEXT

                builder.setView(view)
                builder.setTitle("Comment")
                builder.setPositiveButton("Confirm", this)
                builder.setNegativeButton("Cancel", this)
                ret = builder.create()
            }
        }
        return ret
    }

    override fun onClick(p0: DialogInterface?, p1: Int) {
        val inputStr = input.text.toString()
        when (dialogId) {
            // save inputs
            DUR_DIALOG -> {
                if (inputStr != "") {
                    Util.putDouble(Util.durKey, input.text.toString().toDouble(), sharedPreferences)
                }
            }
            DIST_DIALOG -> {
                if (inputStr != "") {
                    Util.putDouble(Util.distKey, input.text.toString().toDouble(), sharedPreferences)
                }
            }
            CAL_DIALOG -> {
                if (inputStr != "") {
                    Util.putDouble(Util.calKey, input.text.toString().toDouble(), sharedPreferences)
                }
            }
            HR_DIALOG -> {
                if (inputStr != "") {
                    Util.putDouble(Util.hrKey, input.text.toString().toDouble(), sharedPreferences)
                }
            }
            COMM_DIALOG -> {
                val editor = sharedPreferences.edit()
                editor.putString(Util.commKey, input.text.toString())
                editor.apply()
            }
        }
        if (p1 == DialogInterface.BUTTON_POSITIVE) {
            if (inputStr == "") {
                Toast.makeText(activity, "no input", Toast.LENGTH_LONG).show()
                return
            } else {
                Toast.makeText(activity, inputStr, Toast.LENGTH_LONG).show()
            }
        } else if (p1 == DialogInterface.BUTTON_NEGATIVE) {
            Toast.makeText(activity, "cancel clicked", Toast.LENGTH_LONG).show()
        }
    }
}

// adapted from Android Studio docs
class TimePickerFragment: DialogFragment(), TimePickerDialog.OnTimeSetListener  {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        // load time from preferences or use current time
        val sharedPreferences = requireContext().getSharedPreferences(Util.manualPreferences, MODE_PRIVATE)

        val time = sharedPreferences.getLong(Util.timeKey, 0L)
        val hour: Int
        val minute: Int
        if (time != 0L) {
            hour = (time / 100).toInt()
            minute = (time % 100).toInt()
        } else {
            val c = Calendar.getInstance()
            hour = c.get(Calendar.HOUR_OF_DAY)
            minute = c.get(Calendar.MINUTE)
        }
        return TimePickerDialog(activity, this, hour, minute, DateFormat.is24HourFormat(activity))
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        // save time
        val sharedPreferences = requireContext().getSharedPreferences(Util.manualPreferences, MODE_PRIVATE)

        val editor = sharedPreferences.edit()
        editor?.putLong(Util.timeKey, (hourOfDay * 100 + minute).toLong())
        editor?.apply()

        Toast.makeText(activity, "Time: $hourOfDay:$minute", Toast.LENGTH_LONG).show()

    }
}

// adapted from Android Studio docs
class DatePickerFragment: DialogFragment(), DatePickerDialog.OnDateSetListener  {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // load date from preferences or use current date
        val sharedPreferences = requireContext().getSharedPreferences(Util.manualPreferences, MODE_PRIVATE)
        val date = sharedPreferences.getLong(Util.dateKey, 0L)
        val year: Int
        val month: Int
        val day: Int
        if (date != 0L) {
            year = (date / 10000).toInt()
            month = ((date % 10000) / 100).toInt()
            day = (date % 100).toInt()
        } else {
            val calendar = Calendar.getInstance()
            year = calendar.get(Calendar.YEAR)
            month = calendar.get(Calendar.MONTH)
            day = calendar.get(Calendar.DAY_OF_MONTH)
        }
        return DatePickerDialog(requireContext(), this, year, month, day)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        // save time
        val sharedPreferences = requireContext().getSharedPreferences(Util.manualPreferences, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor?.putLong(Util.dateKey, ((year * 10000) + (month * 100) + dayOfMonth).toLong())
        editor?.apply()
        Toast.makeText(activity, "Date: $month/$dayOfMonth/$year", Toast.LENGTH_LONG).show()
    }
}

