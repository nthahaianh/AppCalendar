package com.example.calendarapplication

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private var startWeek = 1
    private var month = 0
    private var year = 0
    var calendar = Calendar.getInstance()
    private var page = 1
    private var titleList: MutableList<String> = mutableListOf()
    val adapter1 = ViewPagerAdapter(supportFragmentManager, lifecycle)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        month = calendar.get(Calendar.MONTH)
        year = calendar.get(Calendar.YEAR)
        main_tvMonthYear?.text = "${month + 1}/$year"

        var sharedPreferences: SharedPreferences =
            this.getSharedPreferences("SharePreferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor?.putInt("spYear", year)
        editor?.putInt("spMonth", month)
        editor?.putInt("spDay", calendar.get(Calendar.DATE))
        editor?.apply()

        var listFragment: MutableList<Fragment1> = mutableListOf()
        listFragment.add(Fragment1().newInstance(startWeek, month, year))
        listFragment.add(Fragment1().newInstance(startWeek, month, year))
        listFragment.add(Fragment1().newInstance(startWeek, month, year))

        adapter1.setLists(listFragment)
        adapter1.update(startWeek, month, year)
        viewpager_demo.apply {
            adapter = adapter1
            currentItem = 1
        }
        viewpager_demo.offscreenPageLimit = 2
        viewpager_demo.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                page = position
            }

            override fun onPageScrollStateChanged(state: Int) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    if (page < 1) {
                        calendar.add(Calendar.MONTH, -1)
                    } else if (page > 1) {
                        calendar.add(Calendar.MONTH, 1)
                    }
                    month = calendar.get(Calendar.MONTH)
                    year = calendar.get(Calendar.YEAR)
                    main_tvMonthYear?.text = "${month + 1}/$year"
                    adapter1.update(startWeek, month, year)
                    viewpager_demo.setCurrentItem(1, false)
                }
            }

        })

        addData()
        setTitleView()
    }

    private fun addData() {
        titleList.add("SAT")
        titleList.add("SUN")
        titleList.add("MON")
        titleList.add("TUE")
        titleList.add("WED")
        titleList.add("THUR")
        titleList.add("FRI")
        titleList.add("SAT")
        titleList.add("SUN")
        titleList.add("MON")
        titleList.add("TUE")
        titleList.add("WED")
        titleList.add("THUR")
        titleList.add("FRI")
    }

    private fun setTitleView() {
        var titleList2: MutableList<String> = mutableListOf()
        for (i in 0..6) {
            titleList2.add(titleList[i + startWeek])
        }
        val dayInWeekAdapter = DayInWeekAdapter(titleList2)
        val titleLayoutManager: RecyclerView.LayoutManager = GridLayoutManager(
            applicationContext,
            7
        )
        rv_title.layoutManager = titleLayoutManager
        rv_title.adapter = dayInWeekAdapter
    }

    fun changeStartWeek(view: View) {
        val strings = arrayOf("SUN", "MON", "TUE", "WED", "THUR", "FRI", "SAT")
        var index = 0
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Select the day start the week")
            .setSingleChoiceItems(
                strings, 0
            ) { _, which -> index = which }
            .setPositiveButton(
                "OK"
            ) { _, _ ->
                startWeek = index + 1
                setTitleView()
                adapter1.update(startWeek, month, year)
            }
            .setNegativeButton(
                "CANCEL"
            ) { _, _ ->
                Toast.makeText(baseContext, "Cancel", Toast.LENGTH_LONG).show()
            }
            .create()
        alertDialog.show()
    }

}