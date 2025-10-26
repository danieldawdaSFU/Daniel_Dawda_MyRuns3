package com.example.daniel_dawda_myruns3.database

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException


// adapted from RoomDatabase demo
class ActivityViewModel(private val repository: ActivityRepository) : ViewModel() {

    val allActivitiesLiveData: LiveData<List<ActivityItem>> = repository.allActivities.asLiveData()


    fun insert(Activity: ActivityItem) {
        repository.insert(Activity)
    }
}

class ActivityViewModelFactory (private val repository: ActivityRepository) : ViewModelProvider.Factory {
    override fun<T: ViewModel> create(modelClass: Class<T>) : T{
        if(modelClass.isAssignableFrom(ActivityViewModel::class.java))
            return ActivityViewModel(repository) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}