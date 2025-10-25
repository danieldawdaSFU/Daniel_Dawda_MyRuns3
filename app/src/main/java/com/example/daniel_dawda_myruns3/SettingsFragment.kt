package com.example.daniel_dawda_myruns3

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.RadioButton
import androidx.fragment.app.Fragment

// adapted from Actiontabs demo
class SettingsFragment:  Fragment() {
    lateinit var profileLayout: LinearLayout
    lateinit var privacyButton: CheckBox
    val settingsPrefKey = "settingsKey"
    val privacyKey = "privacy"
    var privacyChecked: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.settings_fragment, container, false)

        privacyButton = view.findViewById(R.id.privacy)
        loadSettings()

        // if User Profile is clicked
        profileLayout = view.findViewById(R.id.user_profile)
        profileLayout.setOnClickListener {
            val intent = Intent(requireContext(), ProfileActivity::class.java)
            startActivity(intent)
        }

        // privacy button
        privacyButton.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                privacyChecked = 1
                saveSettings()
            } else {
                privacyChecked = 0
                saveSettings()
            }
        }

        val unitsPrefs = view.findViewById<LinearLayout>(R.id.unit_pref)
        unitsPrefs.setOnClickListener {
            val myDialog = SettingsDialogs()
            val bundle = Bundle()
            bundle.putInt(SettingsDialogs.DIALOG_KEY, SettingsDialogs.UNIT_DIALOG)
            myDialog.arguments = bundle
            myDialog.show(parentFragmentManager, "setting dialog")
        }

        val commentDisplay = view.findViewById<LinearLayout>(R.id.comments)
        commentDisplay.setOnClickListener {
            val myDialog = SettingsDialogs()
            val bundle = Bundle()
            bundle.putInt(SettingsDialogs.DIALOG_KEY, SettingsDialogs.COMMENT_DIALOG)
            myDialog.arguments = bundle
            myDialog.show(parentFragmentManager, "setting dialog")
        }

        return view
    }

    fun saveSettings() {
        val settingsPreferences = requireContext().getSharedPreferences(settingsPrefKey, MODE_PRIVATE)
        val editor = settingsPreferences.edit()

        editor.putInt(privacyKey, privacyChecked)
        editor.apply()
    }

    fun loadSettings() {
        val settingsPreferences =
            requireContext().getSharedPreferences(settingsPrefKey, MODE_PRIVATE)

        privacyChecked = settingsPreferences.getInt(privacyKey, 0)
        when (privacyChecked) {
            0 -> privacyButton.isChecked = false
            1 -> privacyButton.isChecked = true
        }

    }
}