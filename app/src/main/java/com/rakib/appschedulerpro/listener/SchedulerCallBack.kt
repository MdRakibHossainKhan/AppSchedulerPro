package com.rakib.appschedulerpro.listener

import com.rakib.appschedulerpro.model.AppData

interface SchedulerCallBack {
    fun passData(appData: AppData)
}