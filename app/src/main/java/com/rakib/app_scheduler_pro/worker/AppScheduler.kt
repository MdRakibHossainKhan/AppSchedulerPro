package com.rakib.app_scheduler_pro.worker

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.work.Worker
import androidx.work.WorkerParameters

class AppScheduler(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    override fun doWork(): Result {
        val packageName = inputData.getString("package")

        val handler = Handler(Looper.getMainLooper())

        handler.postDelayed({
            val intent =
                applicationContext.packageManager.getLaunchIntentForPackage(packageName.toString())
            intent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent?.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT)
            applicationContext.startActivity(intent)
        }, 1000)

        return Result.success()
    }
}