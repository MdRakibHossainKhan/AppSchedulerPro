package com.rakib.app_scheduler_pro.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "schedule")
data class Schedule(
    @PrimaryKey(autoGenerate = true)

    var id: Int,
    var scheduleTime: Long,
    var scheduleTimeInWord: String,
    var appName: String,
    var packageName: String,
    var status: Boolean
)