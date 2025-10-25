package com.example.daniel_dawda_myruns3

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// adapted from ViewModel demo, and GPT
class ProfileViewModel: ViewModel() {
    private val _bitmap = MutableLiveData<Bitmap?>()
    val bitmap: LiveData<Bitmap?> = _bitmap

    fun setImage(bitmap: Bitmap) {
        _bitmap.value = bitmap
    }
}