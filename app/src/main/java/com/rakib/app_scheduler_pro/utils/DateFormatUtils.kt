package com.rakib.app_scheduler_pro.utils

import java.text.SimpleDateFormat
import java.util.Locale

object DateFormatUtils {
    fun convertMilliSecondsToTime(milliSeconds: Long): String {
        val sdf = SimpleDateFormat("d MMM, yyyy | h:mm a", Locale.getDefault())
        return sdf.format(milliSeconds)
    }
}