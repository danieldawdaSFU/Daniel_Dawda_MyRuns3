package com.example.daniel_dawda_myruns3.database

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

// adapted from RoomDatabase demo
class ActivityRepository(private val activityDatabaseDao: ActivityDatabaseDao) {

    val allActivities: Flow<List<ActivityItem>> = activityDatabaseDao.getAllActivities()

    fun insert(comment: ActivityItem){
        CoroutineScope(IO).launch {
            activityDatabaseDao.insertActivity(comment)
        }
    }

    fun delete(id: Long){
        CoroutineScope(IO).launch {
            activityDatabaseDao.deleteActivity(id)
        }
    }

    fun deleteAll(){
        CoroutineScope(IO).launch {
            activityDatabaseDao.deleteAll()
        }
    }
}