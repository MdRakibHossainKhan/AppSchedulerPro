package com.rakib.app_scheduler_pro.scheduler

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.rakib.app_scheduler_pro.R
import com.rakib.app_scheduler_pro.adapter.AppListAdapter
import com.rakib.app_scheduler_pro.base.BaseActivity
import com.rakib.app_scheduler_pro.databinding.ActivitySchedulerBinding
import com.rakib.app_scheduler_pro.listener.SchedulerCallBack
import com.rakib.app_scheduler_pro.model.AppData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SchedulerActivity : BaseActivity<ActivitySchedulerBinding>(),
    DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener,
    SchedulerCallBack {
    lateinit var builder: AlertDialog.Builder
    lateinit var alertDialog: AlertDialog
    lateinit var appRecyclerView: RecyclerView

    override fun getView() = R.layout.activity_scheduler

    override fun onViewReady(savedInstanceState: Bundle?, intent: Intent) {
        binding.addAppScheduleAnimationView.setOnClickListener {
            val appList = installedApps()

            val view = layoutInflater.inflate(R.layout.app_list, null)
            appRecyclerView = view.findViewById(R.id.appRecyclerView)
            appRecyclerView.setHasFixedSize(true)
            builder = AlertDialog.Builder(this)
            builder.setView(view)
            builder.setCancelable(true)
            alertDialog = builder.create()

            val adapter = AppListAdapter(this, alertDialog, this)
            appRecyclerView.adapter = adapter
            adapter.addItem(appList)

            alertDialog.show()
        }
    }

    override fun passData(appData: AppData) {
        this.appData = appData
        showDatePicker(false)
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun installedApps(): MutableList<AppData> {
        val appList: MutableList<AppData>
        val systemAppList = mutableListOf<AppData>()
        val userInstalledAppList = mutableListOf<AppData>()
        val apps = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)

        for (app in apps) {
            try {
                val appName = packageManager.getApplicationLabel(
                    packageManager.getApplicationInfo(
                        app.packageName,
                        PackageManager.GET_META_DATA
                    )
                )

                if (app.flags and (ApplicationInfo.FLAG_UPDATED_SYSTEM_APP or ApplicationInfo.FLAG_SYSTEM) > 0) {
                    // System App
                    systemAppList.add(AppData(1, appName as String, app.packageName))
                } else {
                    // User Installed App
                    userInstalledAppList.add(AppData(1, appName as String, app.packageName))
                }
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }
        }

        appList = (userInstalledAppList + systemAppList) as MutableList<AppData>

        return appList
    }
}