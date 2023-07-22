package com.rakib.app_scheduler_pro.scheduler

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rakib.app_scheduler_pro.R
import com.rakib.app_scheduler_pro.model.Schedule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SchedulerViewModel @Inject constructor(private val schedulerRepository: SchedulerRepository) :
    ViewModel() {
    fun insert(context: Context, schedule: Schedule) {
        viewModelScope.launch {
            val doesExist = schedulerRepository.availableData(schedule.scheduleTimeInWord)
            if (!doesExist) {
                schedulerRepository.insert(schedule)
            } else {
                Toast.makeText(
                    context,
                    context.getString(R.string.schedule_time_is_taken),
                    Toast.LENGTH_LONG
                )
                    .show()
            }
        }
    }

    fun delete(schedule: Schedule) {
        viewModelScope.launch {
            schedulerRepository.deleteSchedule(schedule)
        }
    }

    fun update(schedule: Schedule) {
        viewModelScope.launch {
            schedulerRepository.updateSchedule(schedule)
        }
    }
}