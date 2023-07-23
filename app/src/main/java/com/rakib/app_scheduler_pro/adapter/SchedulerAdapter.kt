package com.rakib.app_scheduler_pro.adapter

import android.content.Context
import android.widget.Toast
import com.rakib.app_scheduler_pro.R
import com.rakib.app_scheduler_pro.base.RecyclerViewAdapter
import com.rakib.app_scheduler_pro.databinding.ScheduledItemBinding
import com.rakib.app_scheduler_pro.listener.SchedulerDeleteCallBack
import com.rakib.app_scheduler_pro.model.Schedule
import java.util.Calendar

class SchedulerAdapter(
    private val context: Context,
    private val callBack: SchedulerDeleteCallBack
) : RecyclerViewAdapter<Schedule, ScheduledItemBinding>() {
    override fun getLayout() = R.layout.scheduled_item

    override fun onBindViewHolder(
        viewHolder: Companion.BaseViewHolder<ScheduledItemBinding>,
        position: Int
    ) {
        viewHolder.binding.apply {
            val item = items[position]
            model = item
        }

        viewHolder.binding.delete.setOnClickListener {
            callBack.deleteData(items[position])
        }

        if (Calendar.getInstance().timeInMillis > items[position].scheduleTime) {
            viewHolder.binding.status.text = context.getString(R.string.executed)
            viewHolder.binding.status.setTextColor(context.getColor(R.color.gray))
        } else {
            if (items[position].status) {
                viewHolder.binding.status.text = context.getString(R.string.active)
            } else {
                viewHolder.binding.status.text = context.getString(R.string.inactive)
                viewHolder.binding.status.setTextColor(context.getColor(R.color.gray))
            }
        }

        viewHolder.binding.status.setOnClickListener {
            if (Calendar.getInstance().timeInMillis < items[position].scheduleTime) {
                if (items[position].status) {
                    callBack.inactiveSchedule(items[position])
                } else {
                    callBack.activeSchedule(items[position])
                }
            } else {
                Toast.makeText(
                    context,
                    context.getString(R.string.schedule_expired),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        viewHolder.binding.edit.setOnClickListener {
            callBack.updateData(items[position])
        }

        val drawable = context.packageManager.getApplicationIcon(items[position].packageName)
        viewHolder.binding.appLogo.setImageDrawable(drawable)
    }
}