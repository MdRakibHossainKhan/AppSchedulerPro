package com.rakib.appschedulerpro.scheduler

import com.rakib.appschedulerpro.model.Schedule
import com.rakib.appschedulerpro.room.ScheduleDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SchedulerRepository @Inject constructor(private val scheduleDAO: ScheduleDAO) {
    suspend fun insert(schedule: Schedule) =
        withContext(Dispatchers.IO) { scheduleDAO.insert(schedule) }

    suspend fun availableData(time: String): Boolean = scheduleDAO.doesDataExist(time)

    suspend fun deleteSchedule(schedule: Schedule) =
        withContext(Dispatchers.IO) { scheduleDAO.deleteSchedule(schedule) }

    suspend fun updateSchedule(schedule: Schedule) = withContext(Dispatchers.IO) {
        scheduleDAO.update(
            schedule.scheduleTime,
            schedule.scheduleTimeInWord,
            schedule.id,
            schedule.status
        )
    }
}