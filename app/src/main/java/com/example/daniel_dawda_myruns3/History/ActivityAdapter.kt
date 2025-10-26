package com.example.daniel_dawda_myruns3.History

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.daniel_dawda_myruns3.R
import com.example.daniel_dawda_myruns3.database.Activity

// adapted from my stress meter app and RoomDatabase demo
class ActivityAdapter(private val context: Context, private val list: List<Activity>): BaseAdapter(){

    val inputMap: Map<Int, String> = mapOf(
        0 to "GPS",
        1 to "Manual",
        2 to "Automatic"
    )

    val activityMap: Map<Int, String> = mapOf(
        0 to "Run",
        1 to "Walk",
        2 to "Cycle",
        3 to "Swim",
        4 to "Row",
        5 to "Other"
    )

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(p0: Int): Any? {
        return list.get(p0)
    }

    override fun getItemId(p0: Int): Long {
        return list.get(p0).id
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {

        val view: View = View.inflate(context, R.layout.list_item,null)

        // find views in list
        val indexView = view.findViewById<TextView>(R.id.top_text)
        val timeView = view.findViewById<TextView>(R.id.bottom_text)

        val inputType = list.get(position).inputType
        val activityType = list.get(position).activityType
        val dateTime = list.get(position).dateTime
        val distance = list.get(position).distance
        val duration = list.get(position).duration

        // construct top and bottom text
        val top_text = "${inputMap[inputType]} Entry: ${activityMap[activityType]}, ${dateTime.toString()}"

        val minutes = duration.toInt() / 60
        val seconds = duration.toInt() % 60
        val bottom_text = "${distance.toString()} Miles, ${minutes.toString()}mins ${seconds.toString()}secs"

        // set top and bottom texts
        indexView.text = top_text
        timeView.text = bottom_text

        return view
    }
}