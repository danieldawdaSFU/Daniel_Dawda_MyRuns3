package com.example.daniel_dawda_myruns3

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.daniel_dawda_myruns3.History.HistoryFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy

// adapted from Actiontabs demo
class MainActivity : AppCompatActivity() {

    lateinit var start: StartFragment
    lateinit var history: HistoryFragment
    lateinit var settings: SettingsFragment
    lateinit var tabLayout: TabLayout
    lateinit var viewPager2: ViewPager2
    lateinit var TabsFragmentAdapter: TabsFragmentAdapter
    lateinit var fragments: ArrayList<Fragment>
    val tabTitles = arrayOf("Start", "History", "Settings") //Tab titles
    lateinit var tabConfigurationStrategy: TabConfigurationStrategy
    lateinit var tabLayoutMediator: TabLayoutMediator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Util.checkPermissions(this)

        tabLayout = findViewById(R.id.tab_layout)
        viewPager2 = findViewById(R.id.view_pager)

        // create fragments list
        fragments = ArrayList()

        // create and add start fragment
        start = StartFragment()
        fragments.add(start)

        // create and add history fragment
        history = HistoryFragment()
        fragments.add(history)

        // create and add settings fragment
        settings = SettingsFragment()
        fragments.add(settings)

        // add fragment adapter
        TabsFragmentAdapter = TabsFragmentAdapter(this, fragments)
        viewPager2.adapter = TabsFragmentAdapter
        
        tabConfigurationStrategy = TabConfigurationStrategy {
                tab: TabLayout.Tab, position: Int ->
            tab.text = tabTitles[position] }
        tabLayoutMediator = TabLayoutMediator(tabLayout, viewPager2, tabConfigurationStrategy)
        tabLayoutMediator.attach()
    }

    override fun onDestroy() {
        super.onDestroy()
        tabLayoutMediator.detach()
    }
}