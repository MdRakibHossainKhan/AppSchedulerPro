package com.rakib.app_scheduler_pro.base

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.rakib.app_scheduler_pro.R
import com.rakib.app_scheduler_pro.home.HomeViewModel
import com.rakib.app_scheduler_pro.model.AppData
import com.rakib.app_scheduler_pro.model.Schedule
import com.rakib.app_scheduler_pro.scheduler.SchedulerViewModel
import com.rakib.app_scheduler_pro.utils.DateFormatUtils
import com.rakib.app_scheduler_pro.worker.AddToSchedule
import java.util.Calendar
import javax.inject.Inject

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity(),
    DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    protected lateinit var binding: T

    val homeViewModel: HomeViewModel by viewModels()
    val schedulerViewModel: SchedulerViewModel by viewModels()

    @Inject
    lateinit var addToSchedule: AddToSchedule

    var scheduleDay: Int = 0
    var scheduleMonth: Int = 0
    var scheduleYear: Int = 0
    var scheduleHour: Int = 0
    var scheduleMinute: Int = 0
    var previousScheduleTime: Long = 0
    var isEdit: Boolean = false

    lateinit var appData: AppData

    private val TAG: String by lazy {
        this.javaClass.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, getView())
        onViewReady(savedInstanceState, intent)
    }

    abstract fun getView(): Int

    abstract fun onViewReady(savedInstanceState: Bundle?, intent: Intent)

    fun showDatePicker(isFromEdit: Boolean) {
        isEdit = isFromEdit

        val calendar: Calendar = Calendar.getInstance()

        val datePickerDialog =
            DatePickerDialog(
                this,
                this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )

        datePickerDialog.show()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        scheduleDay = dayOfMonth
        scheduleYear = year
        scheduleMonth = month

        val calendar: Calendar = Calendar.getInstance()

        val timePickerDialog = TimePickerDialog(
            this, this, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE),
            DateFormat.is24HourFormat(this)
        )

        timePickerDialog.show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        scheduleHour = hourOfDay
        scheduleMinute = minute

        val calendar: Calendar = Calendar.getInstance()
        val currentTimeInMillis: Long = calendar.timeInMillis

        calendar.set(Calendar.DAY_OF_MONTH, scheduleDay)
        calendar.set(Calendar.MONTH, scheduleMonth)
        calendar.set(Calendar.YEAR, scheduleYear)
        calendar.set(Calendar.HOUR, scheduleHour)
        calendar.set(Calendar.MINUTE, scheduleMinute)
        calendar.set(Calendar.SECOND, 0)

        if (currentTimeInMillis < calendar.timeInMillis) {
            Log.d(TAG, "onTimeSet: $currentTimeInMillis ${calendar.timeInMillis}")

            val schedule = Schedule(
                if (isEdit) appData.id else 0,
                calendar.timeInMillis,
                DateFormatUtils.convertMilliSecondsToTime(calendar.timeInMillis),
                appData.appName,
                appData.packageName,
                true
            )

            addToSchedule.addToQueue(schedule.scheduleTime, schedule.packageName)

            if (isEdit) {
                addToSchedule.cancelSchedule(previousScheduleTime.toString())
                schedulerViewModel.update(schedule)
            } else {
                schedulerViewModel.insert(applicationContext, schedule)
                finish()
            }
        } else {
            Toast.makeText(this, this.getString(R.string.select_future_time), Toast.LENGTH_SHORT)
                .show()
        }
    }
}