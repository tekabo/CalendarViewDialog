package com.star.calendarview.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.star.calendarview.Calendar
import com.star.calendarview.CalendarView
import com.star.calendarview.R
import com.star.calendarview.utils.CalendarDateUtils
import java.text.DecimalFormat
import java.util.*

/**
 * @des
 * @date 2022/1/18
 * @author sam
 */
class CalendarDialog(context: Context) : Dialog(context, R.style.StarDialog) {
    private lateinit var mCalendarView: CalendarView

    init {
        initDialogView();
    }

    private fun initDialogView() {
        val view = LayoutInflater.from(context).inflate(R.layout.widget_layout_dialog, null)
        val window = this.window
        window!!.requestFeature(Window.FEATURE_NO_TITLE)
        setContentView(view)
        window.setBackgroundDrawableResource(android.R.color.transparent)

        mCalendarView = view.findViewById<CalendarView>(R.id.widget_calendarView)
        val ivLeft = view.findViewById<ImageView>(R.id.iv_left)
        val ivRight = view.findViewById<ImageView>(R.id.iv_right)
        val tvShowYear = view.findViewById<TextView>(R.id.tv_show_year)
        val tvShowMonth = view.findViewById<TextView>(R.id.tv_show_month)
        val btToday = view.findViewById<TextView>(R.id.bt_today)
        val btMonth = view.findViewById<TextView>(R.id.bt_month)
        val btYear = view.findViewById<TextView>(R.id.bt_year)


        tvShowYear.text = mCalendarView.curYear.toString() + "年"
        tvShowMonth.text = mCalendarView.curMonth.toString() + "月"

        ivLeft.setOnClickListener {
            mCalendarView.scrollToPre()
        }

        ivRight.setOnClickListener {
            mCalendarView.scrollToNext()
        }

        //今日
        btToday.setOnClickListener {
            mListener?.onDateSelected(CalendarDateUtils.today())
            dismiss()
        }
        //本月
        btMonth.setOnClickListener {
            mListener?.onDateRangeSelected(
                CalendarDateUtils.currentMonth() + "-01",
                CalendarDateUtils.today()
            )
            dismiss()
        }
        //本年

        btYear.setOnClickListener {
            mListener?.onDateRangeSelected(
                CalendarDateUtils.year().toString() + "-01-01",
                CalendarDateUtils.today()
            )
            dismiss()
        }

        mCalendarView.setOnMonthChangeListener { year, month ->
            tvShowYear.text = year.toString() + "年"
            tvShowMonth.text = month.toString() + "月"
        }

        mCalendarView.setOnCalendarInterceptListener(object :
            CalendarView.OnCalendarInterceptListener {
            override fun onCalendarIntercept(calendar: Calendar?): Boolean {
                return calendar!!.year > mCalendarView.curYear ||
                        (calendar.year == mCalendarView.curYear && calendar.month > mCalendarView.curMonth) ||
                        (calendar.year == mCalendarView.curYear && calendar.month == mCalendarView.curMonth && calendar.day > mCalendarView.curDay)
            }

            override fun onCalendarInterceptClick(calendar: Calendar?, isClick: Boolean) {
                Toast.makeText(context, "超出选择范围", Toast.LENGTH_SHORT).show()
            }
        })

        mCalendarView.setOnCalendarSelectListener(object : CalendarView.OnCalendarSelectListener {
            override fun onCalendarOutOfRange(calendar: Calendar?) {

            }

            override fun onCalendarSelect(calendar: Calendar?, isClick: Boolean) {
                calendar?.apply {
                    val df = DecimalFormat("00")
                    mListener?.onDateSelected(
                        calendar.year.toString() + "-" + df.format(calendar.month) + "-" + df.format(
                            calendar.day
                        )
                    )
                    dismiss()
                }
            }

        })

    }


    interface CalendarDialogSelectListener {
        fun onDateSelected(date: String)
        fun onDateRangeSelected(startDate: String, endDate: String)
    }

    private var mListener: CalendarDialogSelectListener? = null

    /**
     * 外部使用 - 选择监听
     */
    fun setCalendarDialogSelectListener(listener: CalendarDialogSelectListener) {
        this.mListener = listener
    }

    /**
     * 外部使用 - 回显
     */
    fun setSelectedDate(year: Int, month: Int, day: Int) {
        val calendar = Calendar()
        calendar.year = year
        calendar.month = month
        calendar.day = day
        calendar.schemeColor = Color.parseColor("#D00E1E")
        val map: MutableMap<String, Calendar> = HashMap()
        map[calendar.toString()] = calendar
        mCalendarView.setSchemeDate(map)
        mCalendarView.scrollToCalendar(year, month, day)
    }
}