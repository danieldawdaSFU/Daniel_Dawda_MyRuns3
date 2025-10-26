package com.example.daniel_dawda_myruns3.History

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.daniel_dawda_myruns3.ManualActivity
import com.example.daniel_dawda_myruns3.R
import com.example.daniel_dawda_myruns3.Util
import com.example.daniel_dawda_myruns3.database.Activity
import com.example.daniel_dawda_myruns3.database.ActivityViewModel
import kotlin.text.replace

// adapted from Actiontabs demo
class HistoryFragment : Fragment() {

    // adapted from RoomDatabase demo
    private lateinit var histList: ListView
    private lateinit var arrayList: ArrayList<Activity>
    private lateinit var arrayAdapter: ActivityAdapter
    private lateinit var activityViewModel: ActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.history_fragment, container, false)
        histList = view.findViewById(R.id.history_list)

        arrayList = ArrayList()
        arrayAdapter = ActivityAdapter(requireActivity(), arrayList)
        histList.adapter = arrayAdapter

        val viewModelFactory = ManualActivity
        activityViewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(ActivityViewModel::class.java)

        activityViewModel.allActivitiesLiveData.observe(viewLifecycleOwner) { activities ->
            Log.d("HistoryFragment", "LiveData updated: ${activities.size}")
            arrayAdapter.replace(activities)
            arrayAdapter.notifyDataSetChanged()
        }

        return view
    }
}