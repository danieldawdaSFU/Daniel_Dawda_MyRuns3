package com.example.daniel_dawda_myruns3.History

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.Log
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
class ActivityAdapter(private val context: Context, private var list: List<ActivityItem>, private val onItemClick: (Long) -> Unit): RecyclerView.Adapter<ActivityAdapter.ActivityViewHolder>(){
    inner class ActivityViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        // find views in list
        val topView = itemView.findViewById<TextView>(R.id.top_text)
        val bottomView = itemView.findViewById<TextView>(R.id.bottom_text)

        // assistance from ChatGPT
        init {
            itemView.setOnClickListener {
                val position = getBindingAdapterPosition()
                if (position != RecyclerView.NO_POSITION) {
                    val itemId = list[position].id
                    onItemClick(itemId)
                }
            }
        }
    }

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
        var distance = list.get(position).distance
        val duration = list.get(position).duration

        // construct top and bottom text
        val top_text = "${Util.inputMap[inputType]} Entry: ${Util.activityMap[activityType]}, ${Util.calendarToString(dateTime)}"

        // time conversion
        val (minutes, seconds) = Util.toMinutesAndSeconds(duration)

        // retrieve unit preference
        var unitChecked: Int
        val settingsPreferences = context.getSharedPreferences(Util.settingsPrefKey, MODE_PRIVATE)
        unitChecked = settingsPreferences.getInt(Util.unitKey, 0)


        // distance conversion
        var bottom_text = ""
        if (unitChecked == 0) {
            val rounded = String.format("%.2f", distance)
            bottom_text = "${rounded} Km, ${minutes}mins ${seconds}secs"
        } else {
            distance = Util.kilometersToMiles(distance)
            val rounded = String.format("%.2f", distance)
            bottom_text = "${rounded} Miles, ${minutes}mins ${seconds}secs"
        }

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
        refresh()
    }

    fun refresh() {
        notifyDataSetChanged()
        Log.d("YOPME", "refresh")
    }
}