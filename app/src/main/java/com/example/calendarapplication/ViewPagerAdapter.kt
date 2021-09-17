package com.example.calendarapplication

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import java.util.*

class ViewPagerAdapter(
    fm: FragmentManager
) :
    FragmentPagerAdapter(fm) {
    private val mFragmentManager: FragmentManager? = fm
    var listFragment: MutableList<Fragment1> = mutableListOf()
    override fun getItem(position: Int): Fragment {
        return listFragment[position]
    }

    override fun getCount(): Int {
        return listFragment.size
    }

    fun setLists(list: MutableList<Fragment1>) {
        if (listFragment != null) for (i in 0 until listFragment.size) {
            mFragmentManager?.beginTransaction()?.remove(listFragment[i])
        }
        listFragment = list
    }

    fun update(startWeek: Int,month: Int,year: Int){
        var calendar = Calendar.getInstance()
        calendar.set(year, month, 1)
        var month = calendar.get(Calendar.MONTH)
        var year = calendar.get(Calendar.YEAR)
        listFragment[1].updateCalendar(startWeek,month, year)
        calendar.add(Calendar.MONTH,-1)
        month = calendar.get(Calendar.MONTH)
        year = calendar.get(Calendar.YEAR)
        listFragment[0].updateCalendar(startWeek,month, year)
        calendar.add(Calendar.MONTH,2)
        month = calendar.get(Calendar.MONTH)
        year = calendar.get(Calendar.YEAR)
        listFragment[2].updateCalendar(startWeek,month, year)
    }
}