package com.example.daniel_dawda_myruns3.database

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException


// adapted from RoomDatabase demo
class ActivityViewModel(private val repository: ActivityRepository) : ViewModel() {

    val allActivitiesLiveData: LiveData<List<Activity>> = repository.allActivities.asLiveData()

    fun insert(Activity: Activity) {
        repository.insert(Activity)
    }

    fun deleteFirst(){
        val ActivityList = allActivitiesLiveData.value
        if (ActivityList != null && ActivityList.size > 0){
            val id = ActivityList[0].id
            repository.delete(id)
        }
    }

    fun deleteAll(){
        val ActivityList = allActivitiesLiveData.value
        if (ActivityList != null && ActivityList.size > 0)
            repository.deleteAll()
    }
}

class ActivityViewModelFactory (private val repository: ActivityRepository) : ViewModelProvider.Factory {
    override fun<T: ViewModel> create(modelClass: Class<T>) : T{
        if(modelClass.isAssignableFrom(ActivityViewModel::class.java))
            return ActivityViewModel(repository) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}