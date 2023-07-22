package com.rakib.app_scheduler_pro.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rakib.app_scheduler_pro.model.Schedule

@Database(entities = [Schedule::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun scheduleDao(): ScheduleDAO
}