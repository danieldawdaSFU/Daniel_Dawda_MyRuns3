package com.example.daniel_dawda_myruns3.History

import android.content.Intent
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
import androidx.fragment.app.activityViewModels

// adapted from Actiontabs demo
class HistoryFragment : Fragment() {

    // adapted from RoomDatabase demo
    private lateinit var histrecycler: RecyclerView
    private lateinit var activityViewModel: ActivityViewModel
    private lateinit var adapter: ActivityAdapter
    private val historyViewModel: HistoryViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // update and set history from database
        val view = inflater.inflate(R.layout.history_fragment, container, false)
        histrecycler = view.findViewById(R.id.history_recycler)

        val viewModelFactory = getViewModelFactory(requireContext())
        activityViewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(ActivityViewModel::class.java)

        // RecyclerView setup adapted from ChatGPT
        adapter = ActivityAdapter(requireContext(), activityViewModel.allActivitiesLiveData.value ?: emptyList()
        ) { itemId ->
            Log.d("HistoryFragment", "Item clicked: $itemId")
            val intent = Intent(requireContext(), HistoryItemActivity::class.java)
            intent.putExtra("itemId", itemId)
            startActivity(intent)
        }

        activityViewModel.allActivitiesLiveData.observe(viewLifecycleOwner) { activities ->
            activities.forEach {
                Log.d("DB_CHECK", "Activity in list: id=${it.id}, type=${it.activityType}")
            }
        }

        histrecycler.adapter = adapter
        histrecycler.layoutManager = LinearLayoutManager(requireContext())

        activityViewModel.allActivitiesLiveData.observe(viewLifecycleOwner) { activities ->
            Log.d("HistoryFragment", "LiveData updated: ${activities.size}")
            adapter.replace(activities)
        }

        // adapted from ChatGPT
        historyViewModel.unitChangeSignal.observe(viewLifecycleOwner) {
            adapter.refresh() // adapter reads shared prefs for units
        }



        return view
    }
}