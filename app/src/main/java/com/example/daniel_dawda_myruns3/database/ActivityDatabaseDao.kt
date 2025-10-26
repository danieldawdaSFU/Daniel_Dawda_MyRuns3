package com.example.daniel_dawda_myruns3.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

// adapted from RoomDatabase demo
@Dao
interface ActivityDatabaseDao {

    @Insert
    suspend fun insertActivity(activity: Activity)

    @Query("SELECT * FROM activity_table")
    fun getAllActivities(): Flow<List<Activity>>

    @Query("DELETE FROM activity_table")
    suspend fun deleteAll()

    @Query("DELETE FROM activity_table WHERE id = :key")
    suspend fun deleteActivity(key: Long)
}