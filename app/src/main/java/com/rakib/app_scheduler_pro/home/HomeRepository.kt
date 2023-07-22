package com.rakib.app_scheduler_pro.home

import com.rakib.app_scheduler_pro.model.Schedule
import com.rakib.app_scheduler_pro.room.ScheduleDAO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeRepository @Inject constructor(scheduleDAO: ScheduleDAO) {
    val getScheduleData: Flow<List<Schedule>> = scheduleDAO.getScheduleList()
}