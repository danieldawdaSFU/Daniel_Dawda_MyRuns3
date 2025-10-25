package com.example.daniel_dawda_myruns3.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// adapted from RoomDatabase demo
@Database(entities = [Activity::class], version = 1)
abstract class ActivityDatabase : RoomDatabase() {
    abstract val activityDatabaseDao: ActivityDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: com.example.daniel_dawda_myruns3.database.ActivityDatabase? = null

        fun getInstance(context: Context): com.example.daniel_dawda_myruns3.database.ActivityDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        com.example.daniel_dawda_myruns3.database.ActivityDatabase::class.java,
                        "activity_table"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}