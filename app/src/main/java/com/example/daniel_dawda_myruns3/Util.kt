package com.example.daniel_dawda_myruns3

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

// DEMO from CMPT 362 lecture
object Util {

    val manualPreferences = "manualPrefs"
    val inputKey = "input"
    val actKey = "activity"
    val durKey = "duration"
    val distKey = "distance"
    val calKey = "calories"
    val hrKey = "heartRate"
    val commKey = "comments"
    val dateKey = "date"
    val timeKey = "time"

    fun checkPermissions(activity: Activity?) {
        if (Build.VERSION.SDK_INT < 23) return

        val permissions = mutableListOf<String>()

        if (ContextCompat.checkSelfPermission(activity!!, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.CAMERA)
        }
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.READ_MEDIA_IMAGES)
        }

        if (permissions.isNotEmpty()) {
            ActivityCompat.requestPermissions(activity, permissions.toTypedArray(), 0)
        }
    }

    fun getBitmap(context: Context, imgUri: Uri): Bitmap {
        var bitmap = BitmapFactory.decodeStream(context.contentResolver.openInputStream(imgUri))
        val matrix = Matrix()
        var ret = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        return ret
    }

    // two functions adapted from GPT help for how to save doubles in preferences
    fun putDouble(key: String, value: Double, sharedPreferences: SharedPreferences) {
        val editor = sharedPreferences.edit()
        editor.putLong(key, java.lang.Double.doubleToRawLongBits(value))
        editor.apply()
    }

    fun getDouble(key: String, sharedPreferences: SharedPreferences): Double {
        return java.lang.Double.longBitsToDouble(
            sharedPreferences.getLong(
                key,
                0
            )
        )
    }

    fun parseDate(date: Long): Triple<Int, Int, Int> {
        val year = (date / 10000).toInt()
        val month = ((date % 10000) / 100).toInt()
        val day = (date % 100).toInt()

        return Triple(year, month, day)
    }

    fun parseTime(time: Long): Pair<Int, Int> {
        val hour = (time / 100).toInt()
        val minute = (time % 100).toInt()

        return Pair(hour, minute)
    }
}

