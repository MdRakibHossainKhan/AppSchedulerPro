package com.rakib.app_scheduler_pro.home

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import com.rakib.app_scheduler_pro.R
import com.rakib.app_scheduler_pro.adapter.SchedulerAdapter
import com.rakib.app_scheduler_pro.base.BaseActivity
import com.rakib.app_scheduler_pro.databinding.ActivityHomeBinding
import com.rakib.app_scheduler_pro.listener.SchedulerDeleteCallBack
import com.rakib.app_scheduler_pro.model.AppData
import com.rakib.app_scheduler_pro.model.Schedule
import com.rakib.app_scheduler_pro.scheduler.SchedulerActivity
import com.rakib.app_scheduler_pro.utils.Navigator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener, SchedulerDeleteCallBack {
    private lateinit var builder: AlertDialog.Builder
    private lateinit var alertDialog: AlertDialog

    override fun getView() = R.layout.activity_home

    override fun onViewReady(savedInstanceState: Bundle?, intent: Intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (!Settings.canDrawOverlays(this)) {
                permissionDialog()
            }
        }

        homeViewModel.getScheduleData.observe(this) {
            if (it.isNotEmpty()) {
                binding.emptyScheduleAnimationView.visibility = View.GONE
            } else {
                binding.emptyScheduleAnimationView.visibility = View.VISIBLE
            }

            val adapter = SchedulerAdapter(this, this)
            binding.scheduledAppRecyclerView.setHasFixedSize(true)
            binding.scheduledAppRecyclerView.adapter = adapter
            adapter.addItem(it)
        }

        binding.addAppScheduleButton.setOnClickListener {
            Navigator.navigate(this, SchedulerActivity::class.java)
        }
    }

    override fun deleteData(schedule: Schedule) {
        addToSchedule.cancelSchedule(schedule.scheduleTime.toString())
        schedulerViewModel.delete(schedule)
    }

    override fun updateData(schedule: Schedule) {
        previousScheduleTime = schedule.scheduleTime
        appData = AppData(schedule.id, schedule.appName, schedule.packageName)
        showDatePicker(true)
    }

    override fun inactiveSchedule(schedule: Schedule) {
        schedule.status = false
        addToSchedule.cancelSchedule(schedule.scheduleTime.toString())
        schedulerViewModel.update(schedule)
    }

    override fun activeSchedule(schedule: Schedule) {
        schedule.status = true
        addToSchedule.addToQueue(schedule.scheduleTime, schedule.packageName)
        schedulerViewModel.update(schedule)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE) {
            if (!Settings.canDrawOverlays(this)) {
                checkPermission()
            }
        }
    }

    private fun checkPermission() {
        if (!Settings.canDrawOverlays(this)) {
            val intent =
                Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))

            startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE)
        }
    }

    private fun permissionDialog() {
        builder = AlertDialog.Builder(this)
        builder
            .setCancelable(false)
            .setTitle(getString(R.string.permission_dialog_title))
            .setMessage(getString(R.string.permission_dialog_message))
            .setPositiveButton(getString(R.string.allow)) { dialog, id ->
                checkPermission()
            }
            .setNegativeButton(getString(R.string.deny)) { dialog, id ->
                dialog.cancel()
            }

        alertDialog = builder.create()
        alertDialog.show()
    }

    companion object {
        private const val ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 10
    }
}