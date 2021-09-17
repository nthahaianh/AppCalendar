package com.example.calendarapplication

import android.graphics.Color
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class CalendarAdapter(private var list: MutableList<MyDate>, private val thisMonth: Int) :
    RecyclerView.Adapter<CalendarAdapter.ViewHolder>() {
    lateinit var itemSingleClick: (position: Int) -> Unit
    lateinit var itemDoubleClick: (position: Int) -> Unit

    fun setCallBack(click: (position: Int) -> Unit) {
        itemSingleClick = click
    }

    fun setCallBack2(click: (position: Int) -> Unit) {
        itemDoubleClick = click
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_day1, parent, false)
        val layoutParams = view.layoutParams
        layoutParams.height = (parent.height * 0.16666666).toInt()
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var day = list[position]
        holder.tvDay.text = day.day.toString()
        if (day.select) {
            holder.tvDay.setBackgroundColor(
                Color.rgb(
                    java.util.Random().nextInt(255),
                    java.util.Random().nextInt(255),
                    java.util.Random().nextInt(255)
                )
            )
        }
        if (day.month != thisMonth) {
            holder.tvDay.setTextColor(Color.rgb(100, 100, 100))
            holder.itemView.setBackgroundResource(R.drawable.background2)
        } else {
            holder.tvDay.setTextColor(Color.rgb(0, 0, 0))
            holder.itemView.setBackgroundResource(R.drawable.background1)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvDay: TextView = view.findViewById(R.id.item_day_tvDay)

        init {
            var i = 0
            view.setOnClickListener {
                i++
                val handler = Handler()
                val r = Runnable {
                    if (i == 1) {
                        Log.e(
                            "Click",
                            "---------------------------------------------------------Single click----------------------------------------"
                        )
                        val position: Int = adapterPosition
                        itemSingleClick.invoke(position)
                    }
                    i = 0
                }
                if (i === 1) { //Single click
                    handler?.removeCallbacks(r)
                    handler.postDelayed(r, 300)
                } else if (i === 2) { //Double click
                    handler?.removeCallbacks(r)
                    Log.e(
                        "Click",
                        "---------------------------------------------------------Double click----------------------------------------"
                    )
                    i = 0
                    val position: Int = adapterPosition
                    itemDoubleClick.invoke(position)
                }

            }
        }

    }

}