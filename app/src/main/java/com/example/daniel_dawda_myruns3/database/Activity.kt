package com.example.daniel_dawda_myruns3.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Calendar

// adapted from RoomDatabase demo
@Entity(tableName = "activity_table")
data class Activity (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "input_column")
    var input: Int = 0,

    @ColumnInfo(name = "activity_type_column")
    var activityType: Int = 0,

    @ColumnInfo(name = "dateTime_column")
    var date: Calendar = Calendar.getInstance(),

    @ColumnInfo(name = "duration_column")
    var duration: Double = 0.0,

    @ColumnInfo(name = "distance_column")
    var distance: Double = 0.0,

    @ColumnInfo(name = "avg_pace_column")
    var avgPace: Double = 0.0,

    @ColumnInfo(name = "avg_speed_column")
    var avgSpeed: Double = 0.0,

    @ColumnInfo(name = "calorie_column")
    var calorie: Double = 0.0,

    @ColumnInfo(name = "climb_column")
    var climb: Double = 0.0,

    @ColumnInfo(name = "heart_rate_column")
    var heartRate: Double = 0.0,

    @ColumnInfo(name = "comment_column")
    var comment: String = "",

    @ColumnInfo(name = "location_column")
    var location: ArrayList<LatLng> = ArrayList()
)

class LatLng {
    //TODO: DEFINE CLASS
}
