package com.example.daniel_dawda_myruns3.History

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.daniel_dawda_myruns3.R
import com.example.daniel_dawda_myruns3.Util
import com.example.daniel_dawda_myruns3.database.ActivityItem

// adapted from my stress meter app and RoomDatabase demo
// RecyclerView adapted from ChatGPT assistance
class ActivityAdapter(private val context: Context, private var list: List<ActivityItem>): RecyclerView.Adapter<ActivityAdapter.ActivityViewHolder>(){
    inner class ActivityViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        // find views in list
        val topView = itemView.findViewById<TextView>(R.id.top_text)
        val bottomView = itemView.findViewById<TextView>(R.id.bottom_text)
    }

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

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ActivityAdapter.ActivityViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ActivityViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ActivityViewHolder,
        position: Int
    ) {

        val inputType = list.get(position).inputType
        val activityType = list.get(position).activityType
        val dateTime = list.get(position).dateTime
        val distance = list.get(position).distance
        val duration = list.get(position).duration

        // construct top and bottom text
        val top_text = "${inputMap[inputType]} Entry: ${activityMap[activityType]}, ${Util.calendarToString(dateTime)}"

        val minutes = duration.toInt() / 60
        val seconds = duration.toInt() % 60
        val bottom_text = "${distance} Miles, ${minutes}mins ${seconds}secs"

        // set top and bottom texts
        holder.topView.text = top_text
        holder.bottomView.text = bottom_text
    }


    override fun getItemId(p0: Int): Long {
        return list.get(p0).id
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun replace(newList: List<ActivityItem>) {
        list = newList
        notifyDataSetChanged()
    }
}