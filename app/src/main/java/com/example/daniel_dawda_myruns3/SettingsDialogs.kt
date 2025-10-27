package com.example.daniel_dawda_myruns3

import android.app.Dialog
import android.content.Context.MODE_PRIVATE
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.daniel_dawda_myruns3.History.HistoryViewModel

// adapted from dialog demo
class SettingsDialogs : DialogFragment(), DialogInterface.OnClickListener{
    val settingsPrefKey = "settingsKey"
    val unitKey = "unit"
    var unitChecked: Int = 0
    companion object{
        const val DIALOG_KEY = "dialog"
        const val UNIT_DIALOG = 1
        const val COMMENT_DIALOG = 2
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        lateinit var ret: Dialog
        val bundle = arguments
        val dialogId = bundle?.getInt(DIALOG_KEY)
        if (dialogId == UNIT_DIALOG) {
            val builder = AlertDialog.Builder(requireActivity())
            val view: View = requireActivity().layoutInflater.inflate(R.layout.unit_fragment,
                null)

            loadUnit()
            val radioGroup: RadioGroup = view.findViewById(R.id.unit_radio)

            when (unitChecked) {
                0 -> radioGroup.check(R.id.metric)
                1 -> radioGroup.check(R.id.imperial)
            }

            // generated via GPT
            radioGroup.setOnCheckedChangeListener { group, checkedId ->
                when (checkedId) {
                    R.id.metric -> {
                        unitChecked = 0
                    }
                    R.id.imperial -> {
                        unitChecked = 1
                    }
                }
            }

            builder.setView(view)
            builder.setTitle("Unit Preference")
            builder.setPositiveButton("Confirm", this)
            builder.setNegativeButton("Cancel", this)
            ret = builder.create()
        }
        if (dialogId == COMMENT_DIALOG) {
            val builder = AlertDialog.Builder(requireActivity())
            val view: View = requireActivity().layoutInflater.inflate(R.layout.comment_fragment,
                null)

            builder.setView(view)
            builder.setTitle("Add Comment")
            builder.setPositiveButton("Confirm", this)
            builder.setNegativeButton("Cancel", this)
            ret = builder.create()
        }
        return ret
    }

    override fun onClick(dialog: DialogInterface, item: Int) {
        if (item == DialogInterface.BUTTON_POSITIVE) {
            saveUnit()
            Toast.makeText(activity, "Comment Confirmed", Toast.LENGTH_LONG).show()
        } else if (item == DialogInterface.BUTTON_NEGATIVE) {
            Toast.makeText(activity, "cancel clicked", Toast.LENGTH_LONG).show()
        }
    }

    fun saveUnit() {
        val settingsPreferences = requireContext().getSharedPreferences(settingsPrefKey, MODE_PRIVATE)
        val editor = settingsPreferences.edit()

        editor.putInt(unitKey, unitChecked)
        editor.apply()

        val historyViewModel: HistoryViewModel by activityViewModels()
        historyViewModel.signalUnitChange()
    }

    fun loadUnit() {
        val settingsPreferences = requireContext().getSharedPreferences(settingsPrefKey, MODE_PRIVATE)
        unitChecked = settingsPreferences.getInt(unitKey, 0)
    }
}