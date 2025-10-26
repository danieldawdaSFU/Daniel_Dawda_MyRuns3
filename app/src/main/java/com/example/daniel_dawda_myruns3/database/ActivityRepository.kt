package com.example.daniel_dawda_myruns3.database

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

// adapted from RoomDatabase demo
class ActivityRepository(private val activityDatabaseDao: ActivityDatabaseDao) {

    val allActivities: Flow<List<Activity>> = activityDatabaseDao.getAllActivities()

    suspend fun insert(comment: Activity){
            activityDatabaseDao.insertActivity(comment)
    }

    suspend fun delete(id: Long){
            activityDatabaseDao.deleteActivity(id)
    }

    suspend fun deleteAll(){
            activityDatabaseDao.deleteAll()
    }
}