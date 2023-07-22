package com.rakib.appschedulerpro.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rakib.appschedulerpro.model.Schedule

@Database(entities = [Schedule::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun scheduleDao(): ScheduleDAO
}