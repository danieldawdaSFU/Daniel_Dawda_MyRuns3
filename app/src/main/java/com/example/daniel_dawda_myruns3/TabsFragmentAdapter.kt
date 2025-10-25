package com.example.daniel_dawda_myruns3

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

// adapted from Actiontabs demo
class TabsFragmentAdapter(activity: FragmentActivity, var list: ArrayList<Fragment>) : FragmentStateAdapter(activity){

    override fun createFragment(position: Int): Fragment {
        return list[position]
    }

    override fun getItemCount(): Int {
        return list.size
    }
}