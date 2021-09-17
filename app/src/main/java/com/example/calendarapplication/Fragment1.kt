package com.example.calendarapplication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment1.*
import java.util.*

class Fragment1 : Fragment() {
    private var startWeek = 1
    private var month = Calendar.getInstance().get(Calendar.MONTH)
    private var year = Calendar.getInstance().get(Calendar.YEAR)
    private var dayList: MutableList<MyDate> = mutableListOf()
    lateinit var recyclerView:RecyclerView
    var view1:View?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments
        startWeek = args!!.getInt("startWeek")
        month = args!!.getInt("month")
        year = args!!.getInt("year")
    }

    fun newInstance(startWeek: Int,month: Int,year: Int): Fragment1 {
        val args = Bundle()
        args.putInt("startWeek", startWeek)
        args.putInt("month", month)
        args.putInt("year", year)
        
        val fragment = Fragment1()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment1, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view1 = view
        recyclerView = rv_calendar
        addData(view)
        setCalendarView(view)
    }

    private fun addData(view: View) {
        var calendar = Calendar.getInstance()
        calendar.set(year, month, 1)
        month = calendar.get(Calendar.MONTH)
        var dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        var weekInMonth = calendar.get(Calendar.WEEK_OF_MONTH)
        if (startWeek <= dayOfWeek)
            calendar.add(Calendar.DAY_OF_YEAR, -(7 * (weekInMonth - 1) + (dayOfWeek - startWeek)))
        else
            calendar.add(Calendar.DAY_OF_YEAR, -(7 * weekInMonth + (dayOfWeek - startWeek)))

        for (i in 0..41) {
            dayList.add(MyDate(calendar.get(Calendar.DATE), calendar.get(Calendar.MONTH), false))
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }
    }

    private fun setCalendarView(view: View) {
        val calendarAdapter = CalendarAdapter(dayList, month)
        val calendarLayoutManager: RecyclerView.LayoutManager = GridLayoutManager(
            context,
            7
        )
        rv_calendar.adapter = calendarAdapter
        rv_calendar.layoutManager = calendarLayoutManager

        calendarAdapter.setCallBack {
            for (i in dayList)
                i.select = false
            try {
                dayList[it].select = true
            } catch (exception: Exception) {
            }
            calendarAdapter.notifyDataSetChanged()
        }

        calendarAdapter.setCallBack2 {
            for (i in dayList)
                i.select = false
            try {
                dayList[it].select = true
            } catch (exception: Exception) {
            }
            calendarAdapter.notifyDataSetChanged()
        }
    }

    fun updateCalendar(startWeek: Int,month: Int,year: Int) {
        this.startWeek=startWeek
        this.month = month
        this.year=year
        dayList.clear()
        view1?.let { addData(it) }
        view1?.let { setCalendarView(it) }
    }
}