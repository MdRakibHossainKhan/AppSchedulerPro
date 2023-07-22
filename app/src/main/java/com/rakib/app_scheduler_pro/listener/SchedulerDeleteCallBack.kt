package com.rakib.app_scheduler_pro.listener

import com.rakib.app_scheduler_pro.model.Schedule

interface SchedulerDeleteCallBack {
    fun deleteData(schedule: Schedule)

    fun updateData(schedule: Schedule)

    fun inactiveSchedule(schedule: Schedule)

    fun activeSchedule(schedule: Schedule)
}