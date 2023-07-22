package com.rakib.app_scheduler_pro.utils

import android.content.Context
import android.content.Intent

object Navigator {
    fun <T> navigate(context: Context, target: Class<T>) {
        val intent = Intent(context, target)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        context.startActivity(intent)
    }
}