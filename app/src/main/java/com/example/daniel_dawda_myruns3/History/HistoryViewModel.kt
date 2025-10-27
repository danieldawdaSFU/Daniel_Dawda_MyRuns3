package com.example.daniel_dawda_myruns3.History

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// adapted from GPT
class HistoryViewModel : ViewModel() {
    // LiveData to notify observers that units changed
    private val _unitChangeSignal = MutableLiveData<Unit>()
    val unitChangeSignal: LiveData<Unit> get() = _unitChangeSignal

    fun signalUnitChange() {
        _unitChangeSignal.value = Unit
    }
}