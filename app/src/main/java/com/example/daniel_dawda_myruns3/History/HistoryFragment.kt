package com.example.daniel_dawda_myruns3.History

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.daniel_dawda_myruns3.R
import com.example.daniel_dawda_myruns3.Util.getViewModelFactory
import com.example.daniel_dawda_myruns3.database.ActivityItem
import com.example.daniel_dawda_myruns3.database.ActivityViewModel

// adapted from Actiontabs demo
class HistoryFragment : Fragment() {

    // adapted from RoomDatabase demo
    private lateinit var histrecycler: RecyclerView
    private lateinit var activityViewModel: ActivityViewModel
    private lateinit var adapter: ActivityAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.history_fragment, container, false)
        histrecycler = view.findViewById(R.id.history_recycler)

        val viewModelFactory = getViewModelFactory(requireContext())
        activityViewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(ActivityViewModel::class.java)

        // RecyclerView setup adapted from ChatGPT
        adapter = ActivityAdapter(requireContext(), activityViewModel.allActivitiesLiveData.value ?: emptyList())
        histrecycler.adapter = adapter
        histrecycler.layoutManager = LinearLayoutManager(requireContext())

        activityViewModel.allActivitiesLiveData.observe(viewLifecycleOwner) { activities ->
            Log.d("HistoryFragment", "LiveData updated: ${activities.size}")
            adapter.replace(activities)
        }

        return view
    }
}