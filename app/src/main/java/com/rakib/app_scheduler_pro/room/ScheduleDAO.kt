package com.rakib.app_scheduler_pro.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rakib.app_scheduler_pro.model.Schedule
import kotlinx.coroutines.flow.Flow

@Dao
interface ScheduleDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(model: Schedule)

    @Query("SELECT * FROM schedule ORDER BY id ASC")
    fun getScheduleList(): Flow<List<Schedule>>

    @Query("SELECT EXISTS(SELECT * FROM schedule WHERE scheduleTimeInWord = :time)")
    fun doesDataExist(time: String): Boolean

    @Delete
    fun deleteSchedule(model: Schedule)

    @Query("UPDATE schedule SET scheduleTime = :time,scheduleTimeInWord = :timeInWord,status = :status  WHERE id = :id")
    fun update(time: Long, timeInWord: String, id: Int, status: Boolean)

    @Query("DELETE FROM schedule")
    fun clearScheduleTable()
}