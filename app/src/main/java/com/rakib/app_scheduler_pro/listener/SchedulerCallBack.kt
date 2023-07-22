package com.rakib.app_scheduler_pro.listener

import com.rakib.app_scheduler_pro.model.AppData

interface SchedulerCallBack {
    fun passData(appData: AppData)
}