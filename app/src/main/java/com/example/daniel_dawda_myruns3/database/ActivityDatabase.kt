package com.example.daniel_dawda_myruns3.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

// adapted from RoomDatabase demo
@Database(entities = [Activity::class], version = 1)
@TypeConverters(Converters::class)
abstract class ActivityDatabase : RoomDatabase() {
    abstract val activityDatabaseDao: ActivityDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: ActivityDatabase? = null

        fun getInstance(context: Context): ActivityDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ActivityDatabase::class.java,
                        "activity_table"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}