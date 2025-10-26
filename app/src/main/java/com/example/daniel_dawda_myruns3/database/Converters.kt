package com.example.daniel_dawda_myruns3.database

import androidx.room.TypeConverter
import java.nio.ByteBuffer
import java.util.Calendar
import kotlin.collections.toByteArray

// adapted from https://stackoverflow.com/questions/58362035/inserting-date-inside-a-room-database
// and https://developer.android.com/reference/android/icu/util/Calendar#getTime()
// with a little assistance from ChatGPT

class Converters {

    // convert Calendar object
    @TypeConverter
    fun fromTimestamp(timeStamp: Long?): Calendar? {
        return timeStamp?.let {
            Calendar.getInstance().apply { timeInMillis = it }
        }
    }

    @TypeConverter
    fun calendarToTimestamp(calendar: Calendar): Long? {
        return calendar.getTimeInMillis()

    }

    // convert Array<LatLng> object
    @TypeConverter
    fun fromByteArray(bytes: ByteArray?): ArrayList<LatLng>? {
        val buffer = ByteBuffer.wrap(bytes)
        val latLngList = mutableListOf<LatLng>()
        while (buffer.remaining() >= 16) {
            val lat = buffer.getDouble()
            val lng = buffer.getDouble()
            latLngList.add(LatLng(lat, lng))
        }
        return latLngList as ArrayList<LatLng>
    }

    @TypeConverter
    fun latLngListToBytesList(latLngList: ArrayList<LatLng>): ByteArray {

        val buffer = ByteBuffer.allocate(latLngList.size * 16)
        for (loc in latLngList) {
            buffer.putDouble(loc.latitude)
            buffer.putDouble(loc.longitude)
        }

        return buffer.array()

    }
}