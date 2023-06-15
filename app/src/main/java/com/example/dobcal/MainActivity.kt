package com.example.dobcal

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private var tvSelectedDOB :TextView? = null
    private var tvMinutes :TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDate:Button = findViewById(R.id.btnDatePicker)
        tvSelectedDOB = findViewById(R.id.tvSelected)
        tvMinutes = findViewById(R.id.tvMinutes)
        btnDate.setOnClickListener{
            clickDatePicker()
        }
    }
    private fun clickDatePicker() {
        val myCalendar = Calendar.getInstance()
        var year = myCalendar.get(Calendar.YEAR)
        var month = myCalendar.get(Calendar.MONTH)
        var day = myCalendar.get(Calendar.DAY_OF_MONTH)
        val calDay=DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, selectedYear, selectedMonth, selectedDayOfMonth ->
                Toast.makeText(this,"DOB selected " +
                        "${selectedYear}/${selectedMonth+1}/${selectedDayOfMonth}",
                    Toast.LENGTH_LONG).show()
                // tạo định dạng
                var selected = "${selectedDayOfMonth}/${selectedMonth+1}/${selectedYear}"
                tvSelectedDOB?.text = selected
                //giữ format cho Date
                var sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                val theDay = sdf.parse(selected)
                theDay?.let {
                    //ngày đc tính bằng phút
                    val selectedDayInMinutus = theDay.time /60000
                    // ngày hiện tại
                    val currentDay = sdf.parse(sdf.format(System.currentTimeMillis()))
                    currentDay?.let {
                        val currentDayInMinutes = currentDay.time/60000

                        val cal = (currentDayInMinutes - selectedDayInMinutus).toString()

                        tvMinutes?.text = cal
                    }
                }

            }, year, month, day)
        calDay.datePicker.maxDate = System.currentTimeMillis() - 86400000
        calDay.show()
    }
}