package com.door.calendardialog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.star.calendarview.dialog.CalendarDialog

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val dialog =  CalendarDialog(this)
        dialog.setSelectedDate(2009,11,3)
        dialog.setCalendarDialogSelectListener(object :CalendarDialog.CalendarDialogSelectListener{
            override fun onDateSelected(date: String) {
               Toast.makeText(applicationContext,date,Toast.LENGTH_LONG).show()
            }

            override fun onDateRangeSelected(startDate: String, endDate: String) {
                Toast.makeText(applicationContext,startDate+","+endDate,Toast.LENGTH_LONG).show()
            }

        })
        dialog.show();
    }
}