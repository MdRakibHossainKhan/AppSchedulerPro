package com.rakib.appschedulerpro.home

import com.rakib.appschedulerpro.model.Schedule
import com.rakib.appschedulerpro.room.ScheduleDAO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeRepository @Inject constructor(scheduleDAO: ScheduleDAO) {
    val getScheduleData: Flow<List<Schedule>> = scheduleDAO.getScheduleList()
}