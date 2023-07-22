package com.rakib.appschedulerpro.listener

import com.rakib.appschedulerpro.model.Schedule

interface SchedulerDeleteCallBack {
    fun deleteData(schedule: Schedule)

    fun updateData(schedule: Schedule)

    fun inactiveSchedule(schedule: Schedule)

    fun activeSchedule(schedule: Schedule)
}