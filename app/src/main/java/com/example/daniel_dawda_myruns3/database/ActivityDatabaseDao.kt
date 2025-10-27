package com.example.daniel_dawda_myruns3.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

// adapted from RoomDatabase demo
@Dao
interface ActivityDatabaseDao {

    @Insert
    suspend fun insertActivity(activity: ActivityItem)

    @Query("SELECT * FROM activity_table")
    fun getAllActivities(): Flow<List<ActivityItem>>

    @Query("DELETE FROM activity_table WHERE id = :key")
    suspend fun deleteActivity(key: Long)

    @Query("SELECT * FROM activity_table WHERE id = :key LIMIT 1")
    suspend fun getActivity(key: Long): ActivityItem?
}